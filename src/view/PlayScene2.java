package view;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.StarRaidersMainButton;
import model.Wall;
import model.Zombies;

/**
 * 
 * @author Sungin Hwang
 *
 */
public class PlayScene2 {
	public Pane pane;
	public Scene scene;
	public Scene backScene;
	public Stage stage;
	public PlayerView4 playerView;
	public PlayerView1 playerView1;
	public int zombieTime = 0;
	public int number = 0;
	public Timeline timeline;
	public Timeline timeline1;
	public Timeline timeline2;
	public int scores = 0;
	public int scores1 = 0;
	AnimationTimer timer;
	ArrayList<Zombies> zombies = new ArrayList<>();
	ArrayList<ZombiesView1> mon = new ArrayList<>();
	public Label mark;
	public Label mark1;
	
	public static boolean DOWN = false;
	public static boolean UP = false;
	public static boolean RIGHT = false;
	public static boolean LEFT = false;

	public PlayScene2(int width, int height, Stage stage, Scene backScene) {
		timeline = new Timeline();
		timeline1 = new Timeline();
		timeline2 = new Timeline();
		pane = new Pane();
		scene = new Scene(pane, width, height);
		this.backScene = backScene;
		this.stage = stage;
		createBackground();
		createBackButton();
		createPlayer();
		createMark();
		createMark1();
		
		createZombies();
		createZombies();
		createZombies();
		createZombies();

		
		scores = playerView.getScores();
		scores1 = playerView1.getScores();
		
		timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				playerView.update();
				playerView.update1();
				playerView.update2();
				playerView.update3();
				
				if (scores != playerView.getScores()) {
					pane.getChildren().remove(mark);
					createMark();
				}
				
				playerView1.update();
				playerView1.update1();
				playerView1.update2();
				playerView1.update3();
				
				if (scores1 != playerView1.getScores()) {
					pane.getChildren().remove(mark1);
					createMark1();
				}
				
				if(Math.abs(playerView.player.getxIndex()-playerView1.player.getxIndex())< 0.6 && playerView1.player.getyIndex() - playerView.player.getyIndex() <0.8  && playerView1.player.getyIndex() > playerView.player.getyIndex()) {
					DOWN = true;
				}else {
					DOWN = false;
				}			
				if(Math.abs(playerView.player.getxIndex()-playerView1.player.getxIndex())< 0.6  && playerView.player.getyIndex() - playerView1.player.getyIndex() <0.8  && playerView1.player.getyIndex() < playerView.player.getyIndex()) {
					UP = true;
				}else {
					UP = false;
				}

				if(Math.abs(playerView.player.getyIndex()-playerView1.player.getyIndex())< 0.6  && playerView1.player.getxIndex() - playerView.player.getxIndex() <0.8  && playerView1.player.getxIndex() > playerView.player.getxIndex()) {
					RIGHT = true;
				}else {
					RIGHT = false;
				}
				
				if(Math.abs(playerView.player.getyIndex()-playerView1.player.getyIndex())< 0.6 && playerView.player.getxIndex() - playerView1.player.getxIndex() <0.8  && playerView1.player.getxIndex() < playerView.player.getxIndex()) {
					LEFT = true;
				}else {
					LEFT = false;
				}
				
			}
		};
		timer.start();
	}

	// background
	private void createBackground() {
		Image backgroundImage = new Image("view/resources/playmap.png", scene.getWidth(), scene.getHeight(), false,
				true);
		BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
		pane.setBackground(new Background(background));
	}

	//
	private void createPlayer() {
		playerView = new PlayerView4(pane, zombies, mon, scores);
		pane.getChildren().add(playerView);
		scene.addEventHandler(KeyEvent.KEY_PRESSED, playerView::handlePressed);
		scene.addEventHandler(KeyEvent.KEY_RELEASED, playerView::handleReleased);

		
		playerView1 = new PlayerView1(pane, zombies, mon, scores);
		pane.getChildren().add(playerView1);
		scene.addEventHandler(KeyEvent.KEY_PRESSED, playerView1::handlePressed);
		scene.addEventHandler(KeyEvent.KEY_RELEASED, playerView1::handleReleased);

		Wall wall1 = new Wall(200,200);
		pane.getChildren().add(wall1);
		
		Wall wall2 = new Wall(300,300);
		pane.getChildren().add(wall2);
		
		Wall wall3 = new Wall(400,400);
		pane.getChildren().add(wall3);
		
		Wall wall4 = new Wall(600,300);
		pane.getChildren().add(wall4);
		
		Wall wall5 = new Wall(600,100);
		pane.getChildren().add(wall5);
		
		Wall wall6 = new Wall(170,470);
		pane.getChildren().add(wall6);
		
		Wall wall7 = new Wall(290,0);
		pane.getChildren().add(wall7);
	}

	
	private void createZombies() {
		
		ZombiesView1 zombiesView = new ZombiesView1(playerView, playerView1);
		zombies.add(zombiesView.zombie);
		mon.add(zombiesView);
		pane.getChildren().add(zombiesView);
		number++;

		timeline.setCycleCount(Timeline.INDEFINITE);

		timeline1.setCycleCount(Timeline.INDEFINITE);

		timeline2.setCycleCount(Timeline.INDEFINITE);

		KeyFrame keyFrame1 = new KeyFrame(Duration.seconds(0.02), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				zombiesView.closeMove(zombiesView.getHeight(), zombiesView.getWidth());
			}
		});

		KeyFrame keyFrame2 = new KeyFrame(Duration.seconds(0.1), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				if (zombiesView.zombie.getHp() > 0) {
					// attack animation
					pane.getChildren().remove(zombiesView.atkEffect);
					zombiesView.attack(zombiesView.zombie);
					
					if (zombiesView.en == 1) {
						zombiesView.atkEffect = new ImageView(
								new Image("view/resources/ef1_1.png", 50, 50, true, true));
						zombiesView.atkEffect.setLayoutX(zombiesView.atkx);
						zombiesView.atkEffect.setLayoutY(zombiesView.atky);
						pane.getChildren().add(zombiesView.atkEffect);

					} else if (zombiesView.en == 2) {
						zombiesView.atkEffect = new ImageView(
								new Image("view/resources/ef1_2.png", 50, 50, true, true));
						zombiesView.atkEffect.setLayoutX(zombiesView.atkx);
						zombiesView.atkEffect.setLayoutY(zombiesView.atky);
						pane.getChildren().add(zombiesView.atkEffect);

					} else if (zombiesView.en == 3) {
						zombiesView.atkEffect = new ImageView(
								new Image("view/resources/ef1_3.png", 50, 50, true, true));
						zombiesView.atkEffect.setLayoutX(zombiesView.atkx);
						zombiesView.atkEffect.setLayoutY(zombiesView.atky);
						pane.getChildren().add(zombiesView.atkEffect);

					} else if (zombiesView.en == 4) {
						zombiesView.atkEffect = new ImageView(
								new Image("view/resources/ef1_4.png", 50, 50, true, true));
						zombiesView.atkEffect.setLayoutX(zombiesView.atkx);
						zombiesView.atkEffect.setLayoutY(zombiesView.atky);
						pane.getChildren().add(zombiesView.atkEffect);

					}
				}

				if (zombiesView.player1.getHP() == 100) {
					pane.getChildren().remove(playerView.hpimg);
					playerView.hpimg = new ImageView(new Image("view/resources/hp100.png", 250, 30, false, true));
					playerView.hpimg.setLayoutX(0);
					playerView.hpimg.setLayoutY(570);
					pane.getChildren().add(playerView.hpimg);
				} else if (zombiesView.player1.getHP() == 90) {
					pane.getChildren().remove(playerView.hpimg);
					playerView.hpimg = new ImageView(new Image("view/resources/hp90.png", 250, 30, false, true));
					playerView.hpimg.setLayoutX(0);
					playerView.hpimg.setLayoutY(570);
					pane.getChildren().add(playerView.hpimg);
				} else if (zombiesView.player1.getHP() == 80) {
					pane.getChildren().remove(playerView.hpimg);
					playerView.hpimg = new ImageView(new Image("view/resources/hp80.png", 250, 30, false, true));
					playerView.hpimg.setLayoutX(0);
					playerView.hpimg.setLayoutY(570);
					pane.getChildren().add(playerView.hpimg);
				} else if (zombiesView.player1.getHP() == 70) {
					pane.getChildren().remove(playerView.hpimg);
					playerView.hpimg = new ImageView(new Image("view/resources/hp70.png", 250, 30, false, true));
					playerView.hpimg.setLayoutX(0);
					playerView.hpimg.setLayoutY(570);
					pane.getChildren().add(playerView.hpimg);
				} else if (zombiesView.player1.getHP() == 60) {
					pane.getChildren().remove(playerView.hpimg);
					playerView.hpimg = new ImageView(new Image("view/resources/hp60.png", 250, 30, true, true));
					playerView.hpimg.setLayoutX(0);
					playerView.hpimg.setLayoutY(570);
					pane.getChildren().add(playerView.hpimg);
				} else if (zombiesView.player1.getHP() == 50) {
					pane.getChildren().remove(playerView.hpimg);
					playerView.hpimg = new ImageView(new Image("view/resources/hp50.png", 250, 30, false, true));
					playerView.hpimg.setLayoutX(0);
					playerView.hpimg.setLayoutY(570);
					pane.getChildren().add(playerView.hpimg);
				} else if (zombiesView.player1.getHP() == 40) {
					pane.getChildren().remove(playerView.hpimg);
					playerView.hpimg = new ImageView(new Image("view/resources/hp40.png", 250, 30, false, true));
					playerView.hpimg.setLayoutX(0);
					playerView.hpimg.setLayoutY(570);
					pane.getChildren().add(playerView.hpimg);
				} else if (zombiesView.player1.getHP() == 30) {
					pane.getChildren().remove(playerView.hpimg);
					playerView.hpimg = new ImageView(new Image("view/resources/hp30.png", 250, 30, false, true));
					playerView.hpimg.setLayoutX(0);
					playerView.hpimg.setLayoutY(570);
					pane.getChildren().add(playerView.hpimg);
				} else if (zombiesView.player1.getHP() == 20) {
					pane.getChildren().remove(playerView.hpimg);
					playerView.hpimg = new ImageView(new Image("view/resources/hp20.png", 250, 30, false, true));
					playerView.hpimg.setLayoutX(0);
					playerView.hpimg.setLayoutY(570);
					pane.getChildren().add(playerView.hpimg);
				} else if (zombiesView.player1.getHP() == 10) {
					pane.getChildren().remove(playerView.hpimg);
					playerView.hpimg = new ImageView(new Image("view/resources/hp10.png", 250, 30, false, true));
					playerView.hpimg.setLayoutX(0);
					playerView.hpimg.setLayoutY(570);
					pane.getChildren().add(playerView.hpimg);
				} else if (zombiesView.player1.getHP() == 0) {
					pane.getChildren().remove(playerView.hpimg);
					playerView.hpimg = new ImageView(new Image("view/resources/hp0.png", 250, 30, false, true));
					playerView.hpimg.setLayoutX(0);
					playerView.hpimg.setLayoutY(570);
					pane.getChildren().add(playerView.hpimg);
				}
				
				
				if (zombiesView.player2.getHP() == 100) {
					pane.getChildren().remove(playerView1.hpimg);
					playerView1.hpimg = new ImageView(new Image("view/resources/hp100.png", 250, 30, false, true));
					playerView1.hpimg.setLayoutX(530);
					playerView1.hpimg.setLayoutY(570);
					pane.getChildren().add(playerView1.hpimg);
					
				} else if (zombiesView.player2.getHP() == 90) {
					pane.getChildren().remove(playerView1.hpimg);
					playerView1.hpimg = new ImageView(new Image("view/resources/hp90.png", 250, 30, false, true));
					playerView1.hpimg.setLayoutX(530);
					playerView1.hpimg.setLayoutY(570);;
					pane.getChildren().add(playerView1.hpimg);
				} else if (zombiesView.player2.getHP() == 80) {
					pane.getChildren().remove(playerView1.hpimg);
					playerView1.hpimg = new ImageView(new Image("view/resources/hp80.png", 250, 30, false, true));
					playerView1.hpimg.setLayoutX(530);
					playerView1.hpimg.setLayoutY(570);
					pane.getChildren().add(playerView1.hpimg);
				} else if (zombiesView.player2.getHP() == 70) {
					pane.getChildren().remove(playerView1.hpimg);
					playerView1.hpimg = new ImageView(new Image("view/resources/hp70.png", 250, 30, false, true));
					playerView1.hpimg.setLayoutX(530);
					playerView1.hpimg.setLayoutY(570);
					pane.getChildren().add(playerView1.hpimg);
				} else if (zombiesView.player2.getHP() == 60) {
					pane.getChildren().remove(playerView1.hpimg);
					playerView1.hpimg = new ImageView(new Image("view/resources/hp60.png", 250, 30, true, true));
					playerView1.hpimg.setLayoutX(530);
					playerView1.hpimg.setLayoutY(570);
					pane.getChildren().add(playerView1.hpimg);
				} else if (zombiesView.player2.getHP() == 50) {
					pane.getChildren().remove(playerView1.hpimg);
					playerView1.hpimg = new ImageView(new Image("view/resources/hp50.png", 250, 30, false, true));
					playerView1.hpimg.setLayoutX(530);
					playerView1.hpimg.setLayoutY(570);
					pane.getChildren().add(playerView1.hpimg);
				} else if (zombiesView.player2.getHP() == 40) {
					pane.getChildren().remove(playerView1.hpimg);
					playerView1.hpimg = new ImageView(new Image("view/resources/hp40.png", 250, 30, false, true));
					playerView1.hpimg.setLayoutX(530);
					playerView1.hpimg.setLayoutY(570);
					pane.getChildren().add(playerView1.hpimg);
				} else if (zombiesView.player2.getHP() == 30) {
					pane.getChildren().remove(playerView1.hpimg);
					playerView1.hpimg = new ImageView(new Image("view/resources/hp30.png", 250, 30, false, true));
					playerView1.hpimg.setLayoutX(530);
					playerView1.hpimg.setLayoutY(570);
					pane.getChildren().add(playerView1.hpimg);
				} else if (zombiesView.player2.getHP() == 20) {
					pane.getChildren().remove(playerView1.hpimg);
					playerView1.hpimg = new ImageView(new Image("view/resources/hp20.png", 250, 30, false, true));
					playerView1.hpimg.setLayoutX(530);
					playerView1.hpimg.setLayoutY(570);
					pane.getChildren().add(playerView1.hpimg);
				} else if (zombiesView.player2.getHP() == 10) {
					pane.getChildren().remove(playerView1.hpimg);
					playerView1.hpimg = new ImageView(new Image("view/resources/hp10.png", 250, 30, false, true));
					playerView1.hpimg.setLayoutX(530);
					playerView1.hpimg.setLayoutY(570);
					pane.getChildren().add(playerView1.hpimg);
				} else if (zombiesView.player2.getHP() == 0){
					playerView1.hpimg = new ImageView(new Image("view/resources/hp0.png", 250, 30, false, true));
					pane.getChildren().remove(playerView1.hpimg);
					playerView1.hpimg.setLayoutX(530);
					playerView1.hpimg.setLayoutY(570);
					pane.getChildren().add(playerView1.hpimg);
				}
				
				
				if (zombiesView.player1.getHP() <= 0 || zombiesView.player2.getHP() <= 0) {
					
					pane.getChildren().remove(playerView);
					pane.getChildren().remove(playerView1);

					timer.stop();
					timeline.stop();
					timeline1.stop();
					timeline2.stop();

					SoundManager.playSound("file:src/model/resources/gameover.wav");
					if(SoundManager.clip != null)
						SoundManager.clip.stop();

					GameOverViewManager newManager = new GameOverViewManager();
					stage.hide();
					stage = newManager.getMainStage();
					stage.show();
				}

			}
		});

		KeyFrame keyFrame3 = new KeyFrame(Duration.seconds(4), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				//
				if (zombiesView.zombie.getHp() <= 0) {
					timeline.getKeyFrames().remove(keyFrame1);		
					timeline1.getKeyFrames().remove(keyFrame2);
					pane.getChildren().remove(zombiesView.atkEffect);
				}

				if ((playerView.getScores() < 350 && playerView1.getScores() < 350) && mon.size() < 6) {
					createZombies();
				}
				if ((playerView.getScores() >= 400 || playerView1.getScores() >= 400) && mon.size() <= 1) {

					pane.getChildren().remove(playerView);
					pane.getChildren().remove(playerView1);

					timer.stop();
					timeline.stop();
					timeline1.stop();
					timeline2.stop();

					SoundManager.playSound("file:src/model/resources/gameover.wav");
					if(SoundManager.clip != null)
						SoundManager.clip.stop();
					
					GameSuccessViewManager newManager = new GameSuccessViewManager();
					stage.hide();
					stage = newManager.getMainStage();
					stage.show();
				}
			}
		});

		timeline.getKeyFrames().add(keyFrame1);
		timeline1.getKeyFrames().add(keyFrame2);
		timeline2.getKeyFrames().add(keyFrame3);

		timeline.play();
		timeline1.play();
		timeline2.play();

	}

	public Scene getScene() {
		return scene;
	}

	private void createBackButton() {
		StarRaidersMainButton button = new StarRaidersMainButton("BACK");
		button.setLayoutX(0);
		button.setLayoutY(0);
		pane.getChildren().add(button);

		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				number = 0;
				timer.stop();
				timeline.stop();
				timeline1.stop();
				timeline2.stop();

				stage.hide();
				stage.setScene(backScene);
				stage.show();
				
				SoundManager.music(ViewManager.backsound);
				SoundManager.playSound(ViewManager.start);
			}

		});
	}

	private void createMark() {

		mark = new Label();
		mark.setText("Scores:" + playerView.getScores());
		mark.setFont(Font.font("Roboto Regular", 20));
		mark.setStyle("-fx-text-fill: ivory;");
		mark.setLayoutX(0);
		mark.setLayoutY(35);

		pane.getChildren().add(mark);
	}
	
	private void createMark1() {
		
		mark1 = new Label();
		mark1.setText("Scores:" + playerView1.getScores());
		mark1.setFont(Font.font("Roboto Regular", 20));
		mark1.setStyle("-fx-text-fill: ivory;");
		mark1.setLayoutX(700);
		mark1.setLayoutY(35);

		pane.getChildren().add(mark1);
	}
}
