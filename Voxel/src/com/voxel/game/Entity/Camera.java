package com.voxel.game.Entity;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

public class Camera {
	public float _x, _y, _z;
	public float _dx, _dy, _dz;
	private float _rx, _ry, _rz;
	private float _fov;
	private float _aspect;
	private float _near, _far;
	private float _speed = 0.05f;
	private float _multi = 2f;

	public Camera(float fov, float aspect, float near, float far) {
		_dx = _x = -53f;
		_dy = _y = -34f;
		_dz = _z = -53f;
		_rx = 0;
		_ry = 90;
		_rz = 0;

		this._fov = fov;
		this._aspect = aspect;
		this._near = near;
		this._far = far;
		initProjection();
	}

	public void initProjection() {
		glMatrixMode(GL_PROJECTION);
		glPushMatrix();
		glLoadIdentity();
		gluPerspective(_fov, _aspect, _near, _far);
		glMatrixMode(GL_MODELVIEW);
		glPushMatrix();
		glLoadIdentity();
	}

	public void setView() {
		glRotatef(_rx, 1, 0, 0);
		glRotatef(_ry, 0, 1, 0);
		glRotatef(_rz, 0, 0, 1);
		glTranslatef(_x, _y, _z);
	}

	public void setView2() {
		glMatrixMode(GL_PROJECTION);
		glPushMatrix();
		glLoadIdentity();
		glOrtho(0.0, 900.0, 0, 900.0 / 16 * 9, -3.0, 3.0);
		glMatrixMode(GL_MODELVIEW);
		glPushMatrix();
		glLoadIdentity();
	}

	public float getX() {
		return _x;
	}

	public float getY() {
		return _x;
	}

	public float getZ() {
		return _x;
	}

	public void setX(float x) {
		this._x = x;
	}

	public void setY(float y) {
		this._y = y;
	}

	public void setZ(float z) {
		this._z = z;
	}

	public float getRX() {
		return _rx;
	}

	public float getRY() {
		return _rx;
	}

	public float getRZ() {
		return _rx;
	}

	public void setRX(float rx) {
		this._x = rx;
	}

	public void setRY(float ry) {
		this._y = ry;
	}

	public void setRZ(float rz) {
		this._z = rz;
	}

	public void move(double d, int dir2, boolean run) {
		if (run) _multi = 4f;
		else
			_multi = 2f;
		_z += (d * _speed * _multi) * Math.sin(Math.toRadians(_ry + 90 * dir2));
		_x += (d * _speed * _multi) * Math.cos(Math.toRadians(_ry + 90 * dir2));
	}

	public void rotate(int dir, float move) {
		if (dir == 0) _rx += move * 0.1;
		if (dir == 1) _ry += move * 0.1;
	}
}
