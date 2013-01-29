package com.voxel.game;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.lang.reflect.Field;
import java.nio.FloatBuffer;
import java.util.Arrays;
import java.util.Random;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GLContext;

import com.voxel.game.Utility.Logger;
import com.voxel.game.World.World;

import static org.lwjgl.opengl.GL11.*;

public class VoxelEngine {

	public int WIDTH = 900;
	public int HEIGHT = WIDTH / 16 * 9;
	private boolean running = true;
	private boolean initialize = false;
	Logger log = new Logger();
	private Thread thread;
	private World world;

	public void init() {
		thread = new Thread();
		thread.start();

		initNativeLibs();
		initDisplay();
		initGL();

		initialize = true;
	}

	private void initNativeLibs() {
		switch (LWJGLUtil.getPlatform()) {
		case LWJGLUtil.PLATFORM_MACOSX:
			addLibraryPath("native/macosx");
			break;
		case LWJGLUtil.PLATFORM_LINUX:
			addLibraryPath("native/linux");
			if (System.getProperty("os.arch").contains("64")) {
				System.loadLibrary("openal64");
			} else {
				System.loadLibrary("openal");
			}
			break;
		case LWJGLUtil.PLATFORM_WINDOWS:
			addLibraryPath("native/windows");

			if (System.getProperty("os.arch").contains("64")) {
				System.loadLibrary("OpenAL64");
			} else {
				System.loadLibrary("OpenAL32");
			}
			break;
		default:
			System.out.println("Unsupported operating system");
			System.exit(1);
		}
	}

	private void addLibraryPath(String libPath) {
		try {
			log.out(libPath + " has been chosen for the native file", "info");
			final Field usrPathsField = ClassLoader.class.getDeclaredField("usr_paths");
			usrPathsField.setAccessible(true);
			log.out("Getting user paths", "info");

			final String[] paths = (String[]) usrPathsField.get(null);

			for (String path : paths) {
				if (path.equals(libPath)) return;
			}

			final String[] new_Paths = Arrays.copyOf(paths, paths.length + 1);
			new_Paths[paths.length - 1] = libPath;
			usrPathsField.set(null, new_Paths);

		} catch (Exception e) {
			log.out("Native file added failed", e);
		}
		log.out("Native file added was successful.", "fine");
	}

	private void initDisplay() {
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.setTitle("Voxel" + " | " + "Pre Alpha");
			Display.setResizable(true);
			Display.create();
			log.out("Display was successfuly created", "fine");
		} catch (LWJGLException e) {
			log.out("Cant initialize display", e);
			System.exit(1);
		}
	}

	private void initGL() {
		checkOpenGL();
		initOpenGLParams();
		resizeViewport();
	}

	private void checkOpenGL() {
		boolean canRunGame = GLContext.getCapabilities().OpenGL20 & GLContext.getCapabilities().OpenGL11 & GLContext.getCapabilities().OpenGL12 & GLContext.getCapabilities().OpenGL14 & GLContext.getCapabilities().OpenGL15;

		if (!canRunGame) {
			log.out("Your GPU driver is not supporting the versions of OpenGL that is required. Considered updating your GPU drivers?", "warning");
			System.exit(1);
		} else {
			log.out("Your GPU driver is supporting the versions of OpenGL that is required", "fine");
		}

	}

	private void resizeViewport() {
		glViewport(0, 0, Display.getWidth(), Display.getHeight());
		WIDTH = Display.getWidth();
		HEIGHT = Display.getHeight();
		log.out("Viewport was created at [" + Display.getWidth() + "," + Display.getHeight() + "] and initialized", "fine");
		log.setup(0,300 / 16 * 9-5);
	}

	public void initOpenGLParams() {
		// glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
		glEnableClientState(GL_VERTEX_ARRAY);
		glEnableClientState(GL_COLOR_ARRAY);
		glEnable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_NORMALIZE);
		glDepthFunc(GL_LEQUAL);
		// glEnable(GL_LIGHTING);
		// glEnable(GL_LIGHT0);
		// glShadeModel(GL_SMOOTH);
		glEnable(GL_COLOR_MATERIAL);
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		float LightModelArray[] = new float[] { 0.05f, 0.05f, 0.05f, 0.5f };
		FloatBuffer LightModel = BufferUtils.createFloatBuffer(4);
		LightModel.put(LightModelArray);
		LightModel.position(0);

		float ambientLightArray[] = new float[] { 2f, 2f, 2f, 1.0f };
		FloatBuffer ambientLight = BufferUtils.createFloatBuffer(4);
		ambientLight.put(ambientLightArray);
		ambientLight.position(0);

		glLightModel(GL_LIGHT_MODEL_AMBIENT, LightModel);
		glLight(GL_LIGHT0, GL_AMBIENT, ambientLight);
		log.out("OpenGL params where initialized", "fine");
	}

	public void run() {
		if (!initialize) {
			log.out("Initialize was not successful when first called", "warning");
			log.out("Trying to run initialize again", "info");
			init();
			log.out("Initialize was successful", "fine");
		}

		mainLoop();
	}

	public void cleanUp() {
		if (running) {
			running = false;
			log.out("Clean up has been instantiated", "info");
			Display.destroy();
			log.out("Clean up has been successful", "fine");
		}
	}

	private void mainLoop() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double nano = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;

		world = new World();

		while (running && !Display.isCloseRequested()) {
			long now = System.nanoTime();
			delta += (now - lastTime) / nano;
			lastTime = now;

			while (delta >= 1) {
				world.update(delta);
				updates++;
				delta--;
			}
			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("Locked: " + updates + " | FPS: " + frames);
				Display.setTitle("Voxel" + " | " + "Pre Alpha    Locked: " + updates + "  |  FPS: " + frames);
				updates = 0;
				frames = 0;
				
				Random r = new Random();
				int p = r.nextInt(64);
				log.out("Player["+p+"] "+System.currentTimeMillis(), "info");
			}

			Display.update();

			if (Display.wasResized()) {
				resizeViewport();
			}

		}
		cleanUp();
	}

	private void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		boolean debug = false;

		glLoadIdentity();
		world.render();
		int w = 0;
		int h = 0;
		w = 300;
		h = w / 16 * 9;
		System.out.println(w + "    " + Display.getHeight());
		glColor4f(0f, 0f, 0f, 0.3f);
		glBegin(GL_QUADS);
		{
			glVertex2f(0, h);
			glVertex2f(0, 0);
			glVertex2f(w, 0);
			glVertex2f(w, h);
		}
		glEnd();
		if (debug) {
			log.debugOut();
		}
	}

}
