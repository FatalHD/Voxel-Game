package com.voxel.game.Utility;

import static org.lwjgl.opengl.GL11.glColor3f;
import com.voxel.game.Utility.Text;


public class Logger {

	public static final String[] Default = { "\u001B[39m", "1f", "1f", "1f" };
	public static final String[] Red = { "\u001B[31m", "1f", "0f", "0f" };
	public static final String[] Green = { "\u001B[32m", "0f", "1f", "0f" };
	public static final String[] Yellow = { "\u001B[33m", "1f", "1f", "0f" };
	public static final String[] Blue = { "\u001B[36m", "0f", "0f", "1f" };

	private static String[] message = new String[15];
	private static float[] r = new float[15];
	private static float[] g = new float[15];
	private static float[] b = new float[15];
	private static String outputMessage = "";
	private static int WIDTH;
	private static int HEIGHT;

	public void setup(int width, int height) {
		WIDTH = width;
		HEIGHT = height;
	}

	public void out(String s, String Priority) {
		switch (Priority) {
		case "fine":
			outputMessage = (Green[0] + "[Fine] " + s);
			break;

		case "info":
			outputMessage = (Blue[0] + "[Info] " + s);
			break;

		case "warning":
			outputMessage = (Yellow[0] + "[Warning] " + s);
			break;

		default:
			outputMessage = "Priority is not corrent";
			break;
		}
		if (!outputMessage.substring(outputMessage.length() - 1).equalsIgnoreCase(".")) outputMessage = outputMessage + ".";
		//System.out.println(outputMessage + Default[0]);

		int maxLength = 28;

		for (int i = 0; i < message.length; i++) {

			if (s.length() >= maxLength && i <= message.length - 2) {
			//	String s2 = s.substring(10, 16);
				s = s.substring(0, maxLength);
			//	message[i] = s;
			//	message[i + 1] = s2;
			}

			if (message[message.length - 1] != null) {
				if (i != 0 && i != message.length - 1) {
					setMessage(i, 0, null);
				} else if (i == message.length - 1) {
					setMessage(i, 1, Priority);
					message[message.length - 1] = s;
				}
			}
			if (message[i] == null) {
				message[i] = s;
				switch (Priority) {
				case "fine":
					r[i] = Float.parseFloat(Green[1]);
					g[i] = Float.parseFloat(Green[2]);
					b[i] = Float.parseFloat(Green[3]);
					break;

				case "info":
					r[i] = Float.parseFloat(Blue[1]);
					g[i] = Float.parseFloat(Blue[2]);
					b[i] = Float.parseFloat(Blue[3]);
					break;

				case "warning":
					r[i] = Float.parseFloat(Yellow[1]);
					g[i] = Float.parseFloat(Yellow[2]);
					b[i] = Float.parseFloat(Yellow[3]);
					break;
				}
				break;
			}
		}
	}

	private void setMessage(int i, int mode, String Priority) {
		message[i - 1] = message[i];
		r[i - 1] = r[i];
		g[i - 1] = g[i];
		b[i - 1] = b[i];

		if (mode == 1) {
			switch (Priority) {
			case "fine":
				r[message.length - 1] = Float.parseFloat(Green[1]);
				g[message.length - 1] = Float.parseFloat(Green[2]);
				b[message.length - 1] = Float.parseFloat(Green[3]);
				break;

			case "info":
				r[message.length - 1] = Float.parseFloat(Blue[1]);
				g[message.length - 1] = Float.parseFloat(Blue[2]);
				b[message.length - 1] = Float.parseFloat(Blue[3]);
				break;

			case "warning":
				r[message.length - 1] = Float.parseFloat(Yellow[1]);
				g[message.length - 1] = Float.parseFloat(Yellow[2]);
				b[message.length - 1] = Float.parseFloat(Yellow[3]);
			}
		}
	}

	public void out(String s, Exception e) {
		for (int i = 1; i < e.getStackTrace().length; i++) {
			StackTraceElement err = e.getStackTrace()[i];
			if (err.getClassName().substring(0, 9).equalsIgnoreCase("com.voxel") && !err.getClassName().equalsIgnoreCase("com.voxel.game.Voxel")) {
				String error = "'" + e.getMessage() + "' in the file " + err.getClassName() + " on line " + err.getLineNumber();
				outputMessage = (Red[0] + "[Error] " + s + "\n        " + error);
				if (!outputMessage.substring(outputMessage.length() - 1).equalsIgnoreCase(".")) outputMessage = outputMessage + ".";
				System.out.println(outputMessage + Default[0]);
			}
		}
		System.exit(0);
	}

	public void debugOut() {
		for (int i = 0; i < message.length; i++) {
			if (message[i] != null) {
				glColor3f(r[i], g[i], b[i]);
				Text.string(message[i], WIDTH + 10, HEIGHT - 10 - (10 * i));
				// GL11.glColor3f(Float.parseFloat(Default[1]),
				// Float.parseFloat(Default[2]), Float.parseFloat(Default[3]));
			}
		}
	}
}
