package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

public class MyGdxGame extends ApplicationAdapter {
    SpriteBatch batch;
    Texture bird;
    Texture background;
    Texture bee;
    Texture bee1;
    Texture bee2;
    float birdX = 0;
    float birdY = 0;
    int gamestate = 0;
    float velocty = 0;//ekrana tıklanınca hareket etmesi için
    float gravity = 0.7f;//yer çekimi
    int beeSet = 4;
    float[] enemyX = new float[beeSet];
    float distance = 0;
    float enemyVelocity = 2;
    //DÜŞMAN ARILAR RASTGELE Y EKSENİNDE
    Random random;
    float[] enemyOffset = new float[beeSet];
    float[] enemyOffset2 = new float[beeSet];
    float[] enemyOffset3 = new float[beeSet];
    //HİTBOX-----------------------------------
    Circle birdCrcle;
    Circle[] enemyCircles;
    Circle[] enemyCircles2;
    Circle[] enemyCircles3;
    ShapeRenderer shapeRenderer;//hitbox ı görmek için

    @Override
    public void create() {
        batch = new SpriteBatch();
        background = new Texture("background.png");
        bird = new Texture("bird.png");
        bee = new Texture("bee.png");
        bee1 = new Texture("bee.png");
        bee2 = new Texture("bee.png");
        birdX = Gdx.graphics.getPpcX() / 2 + Gdx.graphics.getPpcX() / 7;
        birdY = (float) Gdx.graphics.getHeight() / 3;
        distance = Gdx.graphics.getWidth() / 2;
        random = new Random();
        shapeRenderer = new ShapeRenderer();

        birdCrcle = new Circle();
        enemyCircles = new Circle[beeSet];
        enemyCircles2 = new Circle[beeSet];
        enemyCircles3 = new Circle[beeSet];


        for (int i = 0; i < beeSet; i++) {
            enemyOffset[i] = (random.nextFloat()) * Gdx.graphics.getHeight();
            enemyOffset2[i] = (random.nextFloat()) * Gdx.graphics.getHeight();
            enemyOffset3[i] = (random.nextFloat()) * Gdx.graphics.getHeight();
            enemyX[i] = Gdx.graphics.getWidth() - bee.getWidth() / 2 + i * distance;//arıların arasondaki mesafe

            enemyCircles[i] = new Circle();
            enemyCircles2[i] = new Circle();
            enemyCircles3[i] = new Circle();
        }

    }

    @Override
    public void render() {
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        if (gamestate == 1) {
            if (Gdx.input.justTouched()) {
                velocty = -15;
            }
            for (int i = 0; i < beeSet; i++) {
                if (enemyX[i] < -bee.getWidth()) {
                    enemyX[i] = enemyX[i] + beeSet * distance;
                    enemyOffset[i] = (random.nextFloat() - 0.5f) * Gdx.graphics.getHeight() - 200;
                    enemyOffset2[i] = (random.nextFloat() - 0.5f) * Gdx.graphics.getHeight() - 200;
                    enemyOffset3[i] = (random.nextFloat() - 0.5f) * Gdx.graphics.getHeight() - 200;
                } else {
                    enemyX[i] = enemyX[i] - enemyVelocity;
                }
                enemyX[i] = enemyX[i] - enemyVelocity;
                batch.draw(bee,
                        enemyX[i],
                        Gdx.graphics.getHeight() / 3 + enemyOffset[i],
                        (float) Gdx.graphics.getWidth() / 18,
                        (float) Gdx.graphics.getHeight() / 10);
                batch.draw(bee1,
                        enemyX[i],
                        Gdx.graphics.getHeight() / 3 + enemyOffset2[i],
                        (float) Gdx.graphics.getWidth() / 18,
                        (float) Gdx.graphics.getHeight() / 10);
                batch.draw(bee2,
                        enemyX[i],
                        Gdx.graphics.getHeight() / 3 + enemyOffset3[i],
                        (float) Gdx.graphics.getWidth() / 18,
                        (float) Gdx.graphics.getHeight() / 10);

                //düşman hitbox ayarlama
                enemyCircles[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 36,
                        Gdx.graphics.getHeight() / 3 + enemyOffset[i] + 50,
                        Gdx.graphics.getWidth() / 36);
                enemyCircles2[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 36,
                        Gdx.graphics.getHeight() / 3 + enemyOffset2[i] + 50,
                        Gdx.graphics.getWidth() / 36);
                enemyCircles3[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 36,
                        Gdx.graphics.getHeight() / 3 + enemyOffset3[i] + 50,
                        Gdx.graphics.getWidth() / 36);

            }

            if (birdY > 0) {
                velocty = velocty + gravity;
                birdY = birdY - velocty;
            } else {
                gamestate = 2;
            }


        } else if (gamestate == 0) {
            if (Gdx.input.justTouched()) {
                gamestate = 1;
            }
        } else if (gamestate == 2) {//oyun bittiğinde
            if (Gdx.input.justTouched()) {
                gamestate = 1;

                birdY = (float) Gdx.graphics.getHeight() / 3;
                for (int i = 0; i < beeSet; i++) {
                    enemyOffset[i] = (random.nextFloat()) * Gdx.graphics.getHeight();
                    enemyOffset2[i] = (random.nextFloat()) * Gdx.graphics.getHeight();
                    enemyOffset3[i] = (random.nextFloat()) * Gdx.graphics.getHeight();
                    enemyX[i] = Gdx.graphics.getWidth() - bee.getWidth() / 2 + i * distance;//arıların arasondaki mesafe

                    enemyCircles[i] = new Circle();
                    enemyCircles2[i] = new Circle();
                    enemyCircles3[i] = new Circle();
                }

                velocty=0;


            }

        }

        batch.draw(bird, birdX + 250
                , birdY + 5
                , (float) Gdx.graphics.getWidth() / 18
                , (float) Gdx.graphics.getHeight() / 10);

        batch.end();
        birdCrcle.set(birdX + Gdx.graphics.getWidth() / 36 - 50, birdY + Gdx.graphics.getHeight() / 20 - 50, (float) Gdx.graphics.getWidth() / 36);

        for (int i = 0; i < beeSet; i++) {
            //çarpışmalarda ne olacak
            if (Intersector.overlaps(birdCrcle, enemyCircles[i]) ||
                    Intersector.overlaps(birdCrcle, enemyCircles2[i]) ||
                    Intersector.overlaps(birdCrcle, enemyCircles3[i])) {
                gamestate = 2;
            }

        }
    }


    @Override
    public void dispose() {

    }

}

