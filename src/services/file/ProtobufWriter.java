package services.file;

import models.Platform;
import models.data.ModelDataHolder;
import models.levels.Level;
import models.players.Player;
import models.shapes.Shape;
import models.shapes.util.ShapePlatformPair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.file.util.ProtobuffGame;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Ahmed Yakout on 1/27/17.
 */
public class ProtobufWriter implements FileWriter {
    private static Logger logger = LogManager.getLogger(JsonWriter.class);
    private final String FILE_EXTENSION = ".protobuff";

    @Override
    public void write(ModelDataHolder dataHolder, String path, String fileName) {
        ProtobuffGame.ProtoGame game = createProtoGame(dataHolder);
        File protobuffFile = new File(path + File.separator +
                fileName + FILE_EXTENSION);
        try {
            logger.debug("Save Path: " + protobuffFile.getAbsolutePath());
            if (!protobuffFile.exists()) {
                protobuffFile.createNewFile();
            }
            FileOutputStream outputStream = new FileOutputStream(protobuffFile);
            game.writeTo(outputStream);
            logger.debug("Data is parsed to protobuff and written sucessfully.");
        } catch (IOException e) {
            logger.error("Error IOException", e);
        }
    }

    private ProtobuffGame.ProtoGame createProtoGame(ModelDataHolder dataHolder) {
        ProtobuffGame.ProtoGame.Builder protoGameBuilder
                = ProtobuffGame.ProtoGame.newBuilder();

        setProtoGameAttributes(protoGameBuilder, dataHolder);
        setProtoGamePlayers(protoGameBuilder, dataHolder);
        setProtoGameShapes(protoGameBuilder, dataHolder);
        setProtoGameShapes(protoGameBuilder, dataHolder);
        setGameMode(protoGameBuilder, dataHolder);
        setActiveLevel(protoGameBuilder, dataHolder.getActiveLevel());

        return protoGameBuilder.build();
    }

    private void setActiveLevel(ProtobuffGame.ProtoGame.Builder protoGameBuilder,
                                Level level) {
        ProtobuffGame.ProtoGame.Level.Builder builder
                = ProtobuffGame.ProtoGame.Level.newBuilder();
        builder.setBackgroundURL(level.getBackgroundURL());
        builder.setClownSpeedRatio(level.getClownSpeedRatio());
        builder.setCurrentLevel(level.getCurrentLevel());
        builder.setNoOfPlatforms(level.getNumPlatforms());
        builder.setMaxX(level.getMaxX());
        builder.setMinY(level.getMinY());
        builder.setMaxY(level.getMaxY());
        builder.setMinX(level.getMinX());
        protoGameBuilder.setActiveLevel(builder);
    }

    private void setGameMode(ProtobuffGame.ProtoGame.Builder protoGameBuilder,
                             ModelDataHolder dataHolder) {
        switch (dataHolder.getGameMode()) {
            case NORMAL:
                protoGameBuilder.setGameMode(ProtobuffGame.ProtoGame.GameMode.NORMAL);
                break;
            case LEVEL:
                protoGameBuilder.setGameMode(ProtobuffGame.ProtoGame.GameMode.LEVEL);
                break;
            case SANDBOX:
                protoGameBuilder.setGameMode(ProtobuffGame.ProtoGame.GameMode.SANDBOX);
                break;
            case TIME_ATTACK:
                protoGameBuilder.setGameMode(ProtobuffGame.ProtoGame.GameMode.TIME_ATTACK);
                break;
        }
    }

    private void setProtoGameShapes(ProtobuffGame.ProtoGame.Builder protoGameBuilder,
                                    ModelDataHolder dataHolder) {
        ProtobuffGame.ProtoGame.ShapePlatformPair.Builder builder
                = ProtobuffGame.ProtoGame.ShapePlatformPair.newBuilder();
        for (ShapePlatformPair shape : dataHolder.getShapes()) {
            ProtobuffGame.ProtoGame.Shape.Builder shapeBuilder
                    = ProtobuffGame.ProtoGame.Shape.newBuilder();
            ProtobuffGame.ProtoGame.Platform.Builder platformBuilder
                    = ProtobuffGame.ProtoGame.Platform.newBuilder();

            setShape(shapeBuilder, shape.getShape());
            setPlatform(platformBuilder, shape.getPlatform());

            builder.setShape(shapeBuilder);
            builder.setPlatform(platformBuilder);
            protoGameBuilder.addShapes(builder);
        }
    }

    private void setPlatform(ProtobuffGame.ProtoGame.Platform.Builder platformBuilder,
                             Platform platform) {
        platformBuilder.setHeight(platform.getHeight().getValue());
        platformBuilder.setWidth(platform.getWidth().getValue());
        switch (platform.getOrientation()) {
            case LEFT:
                platformBuilder.setOrientation(ProtobuffGame.ProtoGame.Platform.Orientation.LEFT);
                break;
            case RIGHT:
                platformBuilder.setOrientation(ProtobuffGame.ProtoGame.Platform.Orientation.RIGHT);
                break;
        }
        platformBuilder.setUrl(platform.getUrl());
        platformBuilder.setPoint(ProtobuffGame.ProtoGame.Point.newBuilder().
                setPropY(platform.getCenter().getY()).setPropX(platform.getCenter().getX()));
    }

    private void setProtoGamePlayers(ProtobuffGame.ProtoGame.Builder protoGameBuilder,
                                     ModelDataHolder dataHolder) {
        // player
        for (Player player : dataHolder.getPlayers()) {
            ProtobuffGame.ProtoGame.Player.Builder protoPlayerBuilder
                    = ProtobuffGame.ProtoGame.Player.newBuilder();
            setProtoPlayerAttributes(protoPlayerBuilder, player);
            protoGameBuilder.addPlayers(protoPlayerBuilder);
        }
    }


    private void setProtoPlayerAttributes(ProtobuffGame.ProtoGame.Player.Builder protoPlayerBuilder,
                                          Player player) {
        switch (player.getInputType()) {
            case JOYSTICK:
                protoPlayerBuilder.setInputType(ProtobuffGame
                        .ProtoGame.Player.InputType.JOYSTICK);
                break;
            case KEYBOARD_PRIMARY:
                protoPlayerBuilder.setInputType(ProtobuffGame
                        .ProtoGame.Player.InputType.KEYBOARD_PRIMARY);
                break;
            case KEYBOARD_SECONDARY:
                protoPlayerBuilder.setInputType(ProtobuffGame
                        .ProtoGame.Player.InputType.KEYBOARD_SECONDARY);
                break;
            case JOYSTICK_PRIMARY:
                protoPlayerBuilder.setInputType(ProtobuffGame
                        .ProtoGame.Player.InputType.JOYSTICK_PRIMARY);
                break;
            case JOYSTICK_SECONDARY:
                protoPlayerBuilder.setInputType(ProtobuffGame
                        .ProtoGame.Player.InputType.JOYSTICK_SECONDARY);
                break;
        }

        protoPlayerBuilder.setSpeed(player.getSpeed());
        protoPlayerBuilder.setLeftStackFull(player.isLeftStackFull());
        protoPlayerBuilder.setRightStackFull(player.isRightStackFull());
        protoPlayerBuilder.setLeftStickUrl(player.getLeftStickUrl());
        protoPlayerBuilder.setRightStickUrl(player.getRightStickUrl());
        protoPlayerBuilder.setPosition(ProtobuffGame.ProtoGame.Point.newBuilder()
                .setPropX(player.getPosition().getX()).setPropY(player.getPosition().getY()));
        protoPlayerBuilder.setPlayerName(player.getName());
        protoPlayerBuilder.setPlayerUrl(player.getPlayerUrl());
        protoPlayerBuilder.setScore(player.getScore());



        for (Shape shape : player.getLeftStack()) {
            ProtobuffGame.ProtoGame.Shape.Builder builder = ProtobuffGame.ProtoGame.Shape.newBuilder();
            setShape(builder, shape);
            protoPlayerBuilder.addLeftStick(builder);
        }

        for (Shape shape : player.getRightStack()) {
            ProtobuffGame.ProtoGame.Shape.Builder builder = ProtobuffGame.ProtoGame.Shape.newBuilder();
            setShape(builder, shape);
            protoPlayerBuilder.addRightStick(builder);
        }
    }

    private void setShape(ProtobuffGame.ProtoGame.Shape.Builder builder, Shape shape) {
        switch (shape.getColor()) {
            case CYAN:
                builder.setColor(ProtobuffGame.ProtoGame.Color.CYAN);
                break;
            case BLACK:
                builder.setColor(ProtobuffGame.ProtoGame.Color.BLACK);
                break;
            case BLUE:
                builder.setColor(ProtobuffGame.ProtoGame.Color.BLUE);
                break;
            case GOLD:
                builder.setColor(ProtobuffGame.ProtoGame.Color.GOLD);
                break;
            case DARKRED:
                builder.setColor(ProtobuffGame.ProtoGame.Color.DARKRED);
                break;
            case GREEN:
                builder.setColor(ProtobuffGame.ProtoGame.Color.GREEN);
                break;
            case ORANGE:
                builder.setColor(ProtobuffGame.ProtoGame.Color.ORANGE);
                break;
            case PINK:
                builder.setColor(ProtobuffGame.ProtoGame.Color.PINK);
                break;
            case PURPLE:
                builder.setColor(ProtobuffGame.ProtoGame.Color.PURPLE);
                break;
            case RED:
                builder.setColor(ProtobuffGame.ProtoGame.Color.RED);
                break;
            case YELLOW:
                builder.setColor(ProtobuffGame.ProtoGame.Color.YELLOW);
                break;
        }
        switch (shape.getState()) {
            case FALLING:
                builder.setState(ProtobuffGame.ProtoGame.Shape.ShapeState.FALLING);
                break;
            case INACTIVE:
                builder.setState(ProtobuffGame.ProtoGame.Shape.ShapeState.INACTIVE);
                break;
            case MOVING_HORIZONTALLY:
                builder.setState(ProtobuffGame.ProtoGame.Shape.ShapeState.MOVING_HORIZONTALLY);
                break;
            case ON_THE_GROUND:
                builder.setState(ProtobuffGame.ProtoGame.Shape.ShapeState.ON_THE_GROUND);
                break;
            case ON_THE_STACK:
                builder.setState(ProtobuffGame.ProtoGame.Shape.ShapeState.ON_THE_STACK);
                break;
        }
        builder.setHeight(shape.getHeight().getValue());
        builder.setWidth(shape.getWidth().getValue());
        builder.setPosition(ProtobuffGame.ProtoGame.Point.newBuilder()
                .setPropX(shape.getPosition().getX()).setPropY(shape.getPosition().getY()));
        builder.setKey(shape.getKey());
    }

    private void setProtoGameAttributes(ProtobuffGame.ProtoGame.Builder protoGameBuilder,
                                        ModelDataHolder dataHolder) {
        protoGameBuilder.setGeneratorCounter(dataHolder.getGeneratorCounter());
        protoGameBuilder.setRemainingTimeAttack(dataHolder.getRemainingTimeAttack());
    }

    @Override
    public String getExtension() {
        return FILE_EXTENSION;
    }
}
