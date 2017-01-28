package services.file;

import controllers.input.InputType;
import javafx.beans.property.SimpleDoubleProperty;
import models.GameMode;
import models.Platform;
import models.Point;
import models.data.ModelDataHolder;
import models.levels.Level;
import models.levels.util.LevelFactory;
import models.players.Player;
import models.shapes.Shape;
import models.shapes.util.ShapeFactory;
import models.shapes.util.ShapePlatformPair;
import models.states.Color;
import models.states.Orientation;
import models.states.ShapeState;
import services.file.util.ProtobuffGame;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Ahmed Yakout on 1/27/17.
 */
public class ProtobufReader implements FileReader {
    private final String EXTENSION = ".protobuff";

    @Override
    public ModelDataHolder read(String path, String fileName) {
        final File protoBuffFile = new File(path
                + File.separator + fileName + EXTENSION);
        ProtobuffGame.ProtoGame protoGame = null;
        try {
            protoGame = ProtobuffGame.ProtoGame.
                    parseFrom(new FileInputStream(protoBuffFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return createModelDataHolder(protoGame);
    }

    private ModelDataHolder createModelDataHolder(ProtobuffGame.ProtoGame protoGame) {
        ModelDataHolder modelDataHolder = new ModelDataHolder();
        setActiveLevel(modelDataHolder, protoGame.getActiveLevel());
        setPlayers(modelDataHolder, protoGame.getPlayersList());
        setShapes(modelDataHolder, protoGame.getShapesList());
        modelDataHolder.setGeneratorCounter(protoGame.getGeneratorCounter());
        modelDataHolder.setRemainingTimeAttack(protoGame.getRemainingTimeAttack());
        setGameMode(modelDataHolder, protoGame.getGameMode());
        return modelDataHolder;
    }

    private void setGameMode(ModelDataHolder modelDataHolder, ProtobuffGame.ProtoGame.GameMode gameMode) {
        switch (gameMode) {
            case LEVEL:
                modelDataHolder.setGameMode(GameMode.LEVEL);
                break;
            case NORMAL:
                modelDataHolder.setGameMode(GameMode.NORMAL);
                break;
            case SANDBOX:
                modelDataHolder.setGameMode(GameMode.SANDBOX);
                break;
            case TIME_ATTACK:
                modelDataHolder.setGameMode(GameMode.TIME_ATTACK);
                break;
        }
    }

    private void setShapes(ModelDataHolder modelDataHolder,
                           List<ProtobuffGame.ProtoGame.ShapePlatformPair> shapesList) {
        Set<ShapePlatformPair> shapes = new HashSet<>();
        for (ProtobuffGame.ProtoGame.ShapePlatformPair shapePlatformPair : shapesList) {
            ShapePlatformPair modelShapePlatformPair = new ShapePlatformPair();
            setPlatform(modelShapePlatformPair, shapePlatformPair.getPlatform());
            modelShapePlatformPair.setShape(buildShape(shapePlatformPair.getShape()));
            shapes.add(modelShapePlatformPair);
        }
        modelDataHolder.setShapes(shapes);
    }

    private Shape buildShape(ProtobuffGame.ProtoGame.Shape shape) {
        Shape modelShape = null;
        Point center = new Point(shape.getPosition().getPropX(), shape.getPosition().getPropY());
        switch (shape.getColor()) {
            case CYAN:
                modelShape = ShapeFactory.getShape(Color.CYAN, shape.getKey());
                break;
            case BLACK:
                modelShape = ShapeFactory.getShape(Color.BLACK, shape.getKey());
                break;
            case BLUE:
                modelShape = ShapeFactory.getShape(Color.BLUE, shape.getKey());
                break;
            case GOLD:
                modelShape = ShapeFactory.getShape(Color.GOLD, shape.getKey());
                break;
            case DARKRED:
                modelShape = ShapeFactory.getShape(Color.DARKRED, shape.getKey());
                break;
            case GREEN:
                modelShape = ShapeFactory.getShape(Color.GREEN, shape.getKey());
                break;
            case ORANGE:
                modelShape = ShapeFactory.getShape(Color.ORANGE, shape.getKey());
                break;
            case PINK:
                modelShape = ShapeFactory.getShape(Color.PINK, shape.getKey());
                break;
            case PURPLE:
                modelShape = ShapeFactory.getShape(Color.PURPLE, shape.getKey());
                break;
            case RED:
                modelShape = ShapeFactory.getShape(Color.RED, shape.getKey());
                break;
            case YELLOW:
                modelShape = ShapeFactory.getShape(Color.YELLOW, shape.getKey());
                break;
        }
        modelShape.setHeight(shape.getHeight());
        modelShape.setPosition(center);
        modelShape.setWidth(shape.getWidth());
        switch (shape.getState()) {
            case FALLING:
                modelShape.setState(ShapeState.FALLING);
                break;
            case INACTIVE:
                modelShape.setState(ShapeState.INACTIVE);
                break;
            case MOVING_HORIZONTALLY:
                modelShape.setState(ShapeState.MOVING_HORIZONTALLY);
                break;
            case ON_THE_GROUND:
                modelShape.setState(ShapeState.ON_THE_GROUND);
                break;
            case ON_THE_STACK:
                modelShape.setState(ShapeState.ON_THE_STACK);
                break;
        }
        return modelShape;
    }

    private void setPlatform(ShapePlatformPair shapePlatformPair,
                             ProtobuffGame.ProtoGame.Platform platform) {
        Platform modelPlatform = null;
        Point center = new Point(platform.getPoint().getPropX(), platform.getPoint().getPropY());
        switch (platform.getOrientation()) {
            case LEFT:
                modelPlatform = new Platform(center, Orientation.LEFT);
                break;
            case RIGHT:
                modelPlatform = new Platform(center, Orientation.RIGHT);
                break;
        }
        modelPlatform.setWidth(new SimpleDoubleProperty(platform.getWidth()));
        modelPlatform.setHeight(new SimpleDoubleProperty(platform.getHeight()));
        modelPlatform.setUrl(platform.getUrl());
        shapePlatformPair.setPlatform(modelPlatform);
    }

    private void setPlayers(ModelDataHolder modelDataHolder,
                            List<ProtobuffGame.ProtoGame.Player> playersList) {
        Set<Player> playerSet = new HashSet<>();

        for (ProtobuffGame.ProtoGame.Player protoPlayer : playersList) {
            Player player = new Player(protoPlayer.getPlayerName());
            player.setSpeed(protoPlayer.getSpeed());
            player.setScore(protoPlayer.getScore());
            switch (protoPlayer.getInputType()) {
                case JOYSTICK:
                    player.setInputType(InputType.JOYSTICK);
                    break;
                case JOYSTICK_PRIMARY:
                    player.setInputType(InputType.JOYSTICK_PRIMARY);
                    break;
                case JOYSTICK_SECONDARY:
                    player.setInputType(InputType.JOYSTICK_SECONDARY);
                    break;
                case KEYBOARD_PRIMARY:
                    player.setInputType(InputType.KEYBOARD_PRIMARY);
                    break;
                case KEYBOARD_SECONDARY:
                    player.setInputType(InputType.KEYBOARD_SECONDARY);
                    break;
            }
            player.setLeftStackFull(protoPlayer.getLeftStackFull());
            player.setRightStackFull(protoPlayer.getRightStackFull());
            player.setPlayerUrl(protoPlayer.getPlayerUrl());
            player.setPosition(new Point(protoPlayer.getPosition().getPropX(),
                    protoPlayer.getPosition().getPropY()));

            for (ProtobuffGame.ProtoGame.Shape shape : protoPlayer.getRightStickList()) {
                player.pushPlateRight(buildShape(shape));
            }
            for (ProtobuffGame.ProtoGame.Shape shape : protoPlayer.getLeftStickList()) {
                player.pushPlateLeft(buildShape(shape));
            }
            playerSet.add(player);
        }

        modelDataHolder.setPlayers(playerSet);
    }

    private void setActiveLevel(ModelDataHolder modelDataHolder, ProtobuffGame.ProtoGame.Level activeLevel) {
        Level level = LevelFactory.getInstance().createLevel(activeLevel.getCurrentLevel(),
                activeLevel.getMinX(), activeLevel.getMinY(),
                activeLevel.getMaxX(), activeLevel.getMaxY());
        level.setBackgroundURL(activeLevel.getBackgroundURL());
        modelDataHolder.setActiveLevel(level);
    }

    @Override
    public String getExtension() {
        return EXTENSION;
    }
}
