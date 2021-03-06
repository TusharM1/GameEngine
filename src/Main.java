import CoreEngine.GameEngine;
import CoreEngine.IGameLoop;
import CoreEngine.Layer;
import Objects.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.stage.Stage;

// TODO Make multithreaded and add server functionality, add key listeners for individual keys - especially for the mouse

public class Main extends Application implements IGameLoop {

	GameEngine gameEngine;

	@Override
	public void init() throws Exception {
		super.init();

		gameEngine = new GameEngine(this, 640, 480);
	}

	@Override
    public void start(Stage primaryStage) {

//		renderQueue.add(new SpaceShip(200, 200, 100, 100));
//		renderQueue.add(new SpaceShip(0, 0, 100, 100));
//		renderQueue.add(new FloatingBall(200, 200, 50, 50));
//		renderQueue.add(new FloatingBall(0, 0, 50, 50));
//		renderQueue.add(new Starfish(200, 200, new double[][]{{0, -75}, {25, -25}, {75, 0}, {25, 25}, {0, 75}, {-25, 25}, {-75, 0}, {-25, -25}}, 0, -75));
//		renderQueue.add(new Starfish(0, 0, new double[][]{{0, -75}, {25, 25}, {0, 15}, {-25, 25}}, 0, 25));
//		renderQueue.add(new Starfish(0, 0, new double[][]{{0, -50}, {50, 0}, {0, 50}, {-50, 0}}, 0, 0));
//		renderQueue.add(new GravityBall(200, 200, 50, 50));
//		renderQueue.add(new DragSquare(200, 200, 100, 100));
//		renderQueue.add(new DragStar(200, 200, new double[][]{{0, -75}, {25, -25}, {75, 0}, {25, 25}, {0, 75}, {-25, 25}, {-75, 0}, {-25, -25}}, 0, -75));

		Layer layer = new Layer();
		layer.add(new BouncingBall(null, null));

//		Layer layer2 = new Layer();
//		layer2.add(new FloatingBall(null, null));

		gameEngine.addLayer(layer);
//		gameEngine.addLayer(layer2);

        primaryStage.setTitle("Game Engine");
        primaryStage.setScene(gameEngine.getScene());
        primaryStage.show();

        gameEngine.start();
    }

    Group subGroup;

	@Override
	public void updateFrame() {

//		if (gameEngine.getKeyboard()[KeyCode.T.getCode()])
//			gameEngine.

//		System.out.println(renderQueue.get(0));
//		Group group = gameEngine.getGroup();
//		subGroup = new Group();
//		for (CoreEngine.GameObject gameObject : renderQueue)
//			subGroup.getChildren().add(gameObject.getNode());
//		group.getChildren().add(subGroup);
	}

    public static void main(String[] args) {
        launch(args);
    }
}
