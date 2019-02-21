package GameEngine;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

public class GameEngine {

	public static IGameLoop game;

	public static int width;
	public static int height;

	public static boolean[] keyboard;
	public static MouseEvent mouse;
	public static Rectangle mouseHitBox;

	public static Canvas canvas;
	public static GraphicsContext graphicsContext;

	public static ArrayList<GameObject> renderQueue;

	Scene scene;
	Group group;
	GameLoop gameLoop;

	public GameEngine(IGameLoop game, int width, int height) {
		this.game = game;
		this.width = width;
		this.height = height;

		gameLoop = new GameLoop();
		renderQueue = new ArrayList<>();

		keyboard = new boolean[65536];
		mouseHitBox = new Rectangle(1, 1);
	}

	public void init() {
		try {
			canvas = new Canvas(width, height);
			graphicsContext = canvas.getGraphicsContext2D();
		} catch (Exception e) {
			e.printStackTrace();
		}

		group = new Group(canvas);
		scene = new Scene(group);

		scene.widthProperty().addListener((observable, oldValue, newValue) -> {
			width = newValue.intValue();
			canvas.setWidth(width);
		});
		scene.heightProperty().addListener((observable, oldValue, newValue) -> {
			height = newValue.intValue();
			canvas.setHeight(height);
		});

		scene.addEventHandler(KeyEvent.ANY, event -> keyboard[event.getCode().getCode()] = event.getEventType().getName().equals("KEY_PRESSED"));
		scene.addEventHandler(MouseEvent.ANY, event -> {
			mouse = event;
			mouseHitBox.setX(event.getX());
			mouseHitBox.setY(event.getY());
		});

//		group.getChildren().add(mouseHitBox);
	}

	public Scene getScene() {
		return scene;
	}

	public Group getGroup() {
		return group;
	}

	public ArrayList<GameObject> getRenderQueue() {
		return renderQueue;
	}

	public void start() {
		gameLoop.start();
	}

	public static void moveToFront(GameObject gameObject) {
		renderQueue.remove(gameObject);
		renderQueue.add(gameObject);
	}

	private class GameLoop extends AnimationTimer {
		@Override
		public void handle(long now) {
			graphicsContext.setFill(Color.CYAN);
			graphicsContext.fillRect(0, 0, width, height);

			// update
			for (int i = 0; i < renderQueue.size(); i++)
				renderQueue.get(i).update();

			// logic
			game.updateFrame();

			// draw
			for (GameObject gameObject : renderQueue)
				gameObject.draw();
		}
	}

	// TODO convert this class into an interface
	// TODO convert all public fields into getters and setters.
	public static abstract class GameObject {

		public double rotation, rotationSpeed,
				acceleration, accelerationX, accelerationY, friction,
				velocity, velocityX, velocityY, maximumVelocity,
				locationX, locationY,
				mass;

		public String name, type;

		public boolean isVisible;

		public GameObject() {
			isVisible = true;
			name = "Untitled";
			type = "GameObject";
		}

		public void update() {
			// Use rectangular bounding box for polygons to check if possibly intersecting, then use more advanced check for polygons
			// break down concave polygons into convex polygons and evaluate each collision separately using separating axis theorem
			rotation = (rotation + rotationSpeed + 360) % 360;

			velocityX += accelerationX;
			velocityY += accelerationY;

			if (friction > 0) {
				velocity = Math.hypot(velocityX, velocityY);

				double frictionX = -friction * velocityX / velocity;
				double frictionY = -friction * velocityY / velocity;

				if (Math.abs(velocityX) > Math.abs(frictionX))
					velocityX += frictionX;
				else
					velocityX = 0;

				if (Math.abs(velocityY) > Math.abs(frictionY))
					velocityY += frictionY;
				else
					velocityY = 0;
			}

			if (maximumVelocity > 0) {
				double maximumVelocityX = maximumVelocity * velocityX / velocity;
				double maximumVelocityY = maximumVelocity * velocityY / velocity;

				if (Math.abs(velocityX) > Math.abs(maximumVelocityX))
					velocityX = maximumVelocityX;
				if (Math.abs(velocityY) > Math.abs(maximumVelocityY))
					velocityY = maximumVelocityY;
			}

			locationX += velocityX;
			locationY += velocityY;
		}

		public final void draw() {
			if (isVisible) {
				graphicsContext.save();
				drawObject();
				graphicsContext.restore();
			}
//			graphicsContext.setFill(Color.BLACK);
//			graphicsContext.fillRect(width / 2 - 1, 0, 2, height);
//			graphicsContext.fillRect(0, height / 2 - 1, width, 2);
		}

		protected abstract void drawObject();

		public abstract Shape getHitBox();

		@Override
		public String toString() {
//			return "Location X: " + locationX + "\n" +
//					"Location Y: " + locationY + "\n" +
//					"Velocity X: " + velocityX + "\n" +
//					"Velocity Y: " + velocityY + "\n" +
//					"Acceleration X: " + accelerationX + "\n" +
//					"Acceleration Y: " + accelerationY + "\n" +
//					"Rotation: " + rotation + "\n" +
//					"Rotation Speed: " + rotationSpeed + "\n";
			return String.format("[%s: %s]", name, type);
//			return super.toString();
		}

//		public double getRotation() { return rotation; }
//		public void setRotation(double rotation) { this.rotation = rotation; }
//		public double getRotationSpeed() { return rotationSpeed; }
//		public void setRotationSpeed(double rotationSpeed) { this.rotationSpeed = rotationSpeed; }
//		public double getAccelerationX() { return accelerationX; }
//		public void setAccelerationX(double accelerationX) { this.accelerationX = accelerationX; }
//		public double getAccelerationY() { return accelerationY; }
//		public void setAccelerationY(double accelerationY) { this.accelerationY = accelerationY; }
//		public double getVelocityX() { return velocityX; }
//		public void setVelocityX(double velocityX) { this.velocityX = velocityX; }
//		public double getVelocityY() { return velocityY; }
//		public void setVelocityY(double velocityY) { this.velocityY = velocityY; }
//		public double getLocationX() { return locationX; }
//		public void setLocationX(double locationX) { this.locationX = locationX; }
//		public double getLocationY() { return locationY; }
//		public void setLocationY(double locationY) { this.locationY = locationY; }
//		public double getMass() { return mass; }
//		public void setMass(double mass) { this.mass = mass; }
//		public boolean isVisible() { return isVisible; }
//		public void setVisible(boolean visible) { isVisible = visible; }

	}

}
