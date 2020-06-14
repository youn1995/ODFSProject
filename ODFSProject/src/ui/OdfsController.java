package ui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import saying.AppService;
import saying.AppServiceImpl;
import saying.FamousSay;
import saying.FamousSayProperty;
import saying.Member;

public class OdfsController implements Initializable {

	@FXML
	Button btnLogin, btnSignUp;
	@FXML
	Label labTodayFS, labTodayFSWho;
	@FXML
	TextField txtUserId;
	@FXML
	PasswordField txtPwd;

	Stage primaryStage;

	Member member = null;
	FamousSay famousSay = null;
	List<Integer> userLikeList = new ArrayList<>();
	List<Integer> userNoLikeList = new ArrayList<>();

	AppService app = new AppServiceImpl();

	final ToggleGroup group = new ToggleGroup();
	ToggleButton toBtnLikeIt;
	ToggleButton toBtnNoLike;

	// for hyLinkPre
	int userHyLinkPreNextCheckNum = 0;
	boolean isPreFSExist = true;
	FamousSay savePre = null;
	FamousSay famPre = null;

	// for hyLinkNext
//	int userHyLinkNextCheckNum = 0;

	
	public void setPrimaryStage(Stage stage) {
		this.primaryStage = stage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		btnLogin.setOnAction((ActionEvent event) -> btnLoginAction(event));
		btnSignUp.setOnAction((ActionEvent event) -> btnSignUpAction(event));
		FSToday();
	
		labTodayFS.setText(famousSay.getContent());
		labTodayFSWho.setText(famousSay.getName());

//		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
//
//			@Override
//			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
//				btnChangeSelect(oldValue, newValue);
//			}
//		});
	}

	public void btnLoginAction(ActionEvent e) {

		AppService app = new AppServiceImpl();
		member = app.login(txtUserId.getText(), txtPwd.getText());
		if (member.getName() == null || member == null) {
			messagePopup("아이디 또는 비밀번호를 틀렸습니다", btnLogin);
		} else if (member.getUserId() == 1000) {
			Parent parent;
			try {
				parent = FXMLLoader.load(getClass().getResource("AdminPage.fxml"));
				Scene scene = new Scene(parent);
				primaryStage.setScene(scene);
				primaryStage.show();
				adminManager(parent);

			} catch (IOException e1) {
				e1.printStackTrace();
			}

		} else {
			try {
				Parent parent = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
				Scene scene = new Scene(parent);
				primaryStage.setScene(scene);
				primaryStage.show();

				toggleLIke(parent);

				Label labContent = (Label) parent.lookup("#labContent");
				labContent.setText(famousSay.getContent());
				Label labWho = (Label) parent.lookup("#labWho");
				labWho.setText(famousSay.getName());
				Button btnLikeList = (Button) parent.lookup("#btnLikeList");
				btnLikeList.setOnAction(event -> {
					listOfUserLike(event);
				});
				Hyperlink hyLinkPre = (Hyperlink) parent.lookup("#hyLinkBefore");
				hyLinkPre.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						famPre = app.getPreviousFamousSay(userHyLinkPreNextCheckNum-1);
						if(famPre.getContent() == null) {
							labWho.setText(savePre.getName());
							labContent.setText(savePre.getContent());
//						FamousSay famPre = app.getPreviousFamousSay(userHyLinkPreNextCheckNum);
						} else {
							labWho.setText(famPre.getName());
							labContent.setText(famPre.getContent());
							savePre = famPre;
							userHyLinkPreNextCheckNum--;
						}
						
						
						
//					
					}
				});

				Hyperlink hyLinkNext = (Hyperlink) parent.lookup("#hyLinkNext");
				hyLinkNext.setOnMouseClicked(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
//						if(famousSay.getContent().equals(labContent.getText())) {
//							FamousSay famNextFS = app.getNextFamousSay(1);
//							labWho.setText(famNextFS.getName());
//							labContent.setText(famNextFS.getContent());
//						} else {
//							userHyLinkPreCheckNum++;
//							FamousSay famNextFS = app.getPreviousFamousSay(userHyLinkPreCheckNum);
//							labWho.setText(famNextFS.getName());
//							labContent.setText(famNextFS.getContent());
//						}

					}
				});

				Hyperlink hyLinkReturnToday = (Hyperlink) parent.lookup("#hyLinkReturnToday");
				hyLinkReturnToday.setOnMouseClicked(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
//						labWho.setText(famousSay.getContent());
//						labContent.setText(famousSay.getName());
					}
				});

				Hyperlink hyLinkLogOut = (Hyperlink) parent.lookup("#hyLinkLogout");

//				hyLinkLogOut.setOnMouseClicked(event -> { 				
//	
//				});

			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}
	}

	public void btnSignUpAction(ActionEvent e) {
		Stage addStage = new Stage(StageStyle.UTILITY);
		addStage.initModality(Modality.WINDOW_MODAL);
		addStage.initOwner(btnSignUp.getScene().getWindow());
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("SignInPage.fxml"));
			Scene scene = new Scene(parent);
			addStage.setScene(scene);
			addStage.setResizable(false);
			addStage.show();

			Hyperlink hyLinkGoBack = (Hyperlink) parent.lookup("#hyLinkGoBack");
			hyLinkGoBack.setOnMouseClicked(event -> {
				addStage.close();
			});
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	public void messagePopup(String message, Parent test) {
		HBox hbox = new HBox();
		hbox.setStyle("-fx-background-color: black; -fx-background-radius: 20;");
		hbox.setAlignment(Pos.CENTER);
		ImageView imageView = new ImageView();
		imageView.setImage(new Image("/icons/dialog-warning.png"));
		imageView.setFitHeight(30);
		imageView.setFitWidth(30);
		Label label = new Label();
		label.setText(message);
		label.setStyle("-fx-text-fill: white");
		HBox.setMargin(label, new Insets(0, 5, 0, 5));
		hbox.getChildren().add(imageView);
		hbox.getChildren().add(label);
		Popup popup = new Popup();
		popup.getContent().add(hbox);
		popup.setAutoHide(true);
		popup.show(test.getScene().getWindow());
	}

	public void FSToday() {
		AppService app = new AppServiceImpl();
		famousSay = app.getDailyFS();

	}

	public void toggleLIke(Parent parent) {

		userLikeList = app.getUserLikeList(1, member.getUserId());
		userNoLikeList = app.getUserLikeList(-1, member.getUserId());
		toBtnLikeIt = (ToggleButton) parent.lookup("#toBtnLikeIt");
		toBtnNoLike = (ToggleButton) parent.lookup("#toBtnNoLike");

		toBtnLikeIt.setToggleGroup(group);
		toBtnNoLike.setToggleGroup(group);
		boolean isLike = false;
		boolean isNoLike = false;
		for (int i = 0; i < userLikeList.size(); i++) {
			if (userLikeList.get(i) == famousSay.getListId()) {
				isLike = true;
				break;
			}
		}
		for (int i = 0; i < userNoLikeList.size(); i++) {
			if (userNoLikeList.get(i) == famousSay.getListId()) {
				isNoLike = true;
				break;
			}
		}

		if (isLike) {
			toBtnNoLike.setDisable(isLike);
			toBtnLikeIt.setSelected(isLike);
		} else if (isNoLike) {
			toBtnLikeIt.setDisable(isNoLike);
			toBtnNoLike.setSelected(isNoLike);
		}

		toBtnLikeIt.setOnAction((ActionEvent event) -> handleToBtnAction(event));
		toBtnNoLike.setOnAction((ActionEvent event) -> handleToBtnAction(event));
	}

	public void handleToBtnAction(ActionEvent e) {
		if (toBtnLikeIt.isArmed() && !toBtnLikeIt.isSelected()) {
			System.out.println("좋아요가 취소");
			app.insertDeleteUserLikeList(2, famousSay.getListId(), member.getUserId());
			toBtnNoLike.setDisable(false);
		} else if (toBtnLikeIt.isSelected()) {
			System.out.println("좋아요");
			app.insertDeleteUserLikeList(1, famousSay.getListId(), member.getUserId());
			toBtnNoLike.setDisable(true);
		} else if (toBtnNoLike.isArmed() && !toBtnNoLike.isSelected()) {
			System.out.println("싫어요가 취소됨");
			toBtnLikeIt.setDisable(false);
			app.insertDeleteUserLikeList(-2, famousSay.getListId(), member.getUserId());
		} else if (toBtnNoLike.isSelected()) {
			System.out.println("싫어요");
			toBtnLikeIt.setDisable(true);
			app.insertDeleteUserLikeList(-1, famousSay.getListId(), member.getUserId());
		}
	}

	public void listOfUserLike(ActionEvent e) {
		Stage addStage = new Stage(StageStyle.UTILITY);
		addStage.initModality(Modality.WINDOW_MODAL);
		addStage.initOwner(btnSignUp.getScene().getWindow());
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("ListUserLike.fxml"));
			Scene scene = new Scene(parent);
			addStage.setScene(scene);
			addStage.setResizable(false);
			addStage.show();

			TableView<FamousSayProperty> tableView = (TableView<FamousSayProperty>) parent.lookup("#tableViewLike");
			ObservableList<FamousSayProperty> famousList = (ObservableList<FamousSayProperty>) app
					.userLikeList(member.getUserId());
			TableColumn<FamousSayProperty, String> tcName = new TableColumn<FamousSayProperty, String>();
			tcName.setCellValueFactory(new PropertyValueFactory<FamousSayProperty, String>("nameSim"));
			TableColumn<FamousSayProperty, String> tcContent = new TableColumn<FamousSayProperty, String>();
			tcContent.setCellValueFactory(new PropertyValueFactory<FamousSayProperty, String>("contentSim"));
			tcName.setText("이름");
			tcContent.setText("Content");
			tableView.getColumns().add(tcName);
			tableView.getColumns().add(tcContent);
			tableView.setItems(famousList);

			Hyperlink hyLinkGoBack = (Hyperlink) parent.lookup("#hyLinkGoBack");
			hyLinkGoBack.setOnMouseClicked(event -> {
				
				addStage.close();
			});
			
			addStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent event) {

				}
			});
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void adminUserManage(Parent parent) {

	}

	public void adminManager(Parent parent) {
		Button btnDeleteFS = (Button) parent.lookup("#btnDeleteFS");
		Button btnUserManage = (Button) parent.lookup("#btnUserManage");
		Button btnAddFS = (Button) parent.lookup("#btnAddFS");
		Button btnExit = (Button) parent.lookup("#btnExit");
		TextField txtTodayFSContent = (TextField) parent.lookup("#txtTodayFSContent");
		TextField txtTodayFSName = (TextField) parent.lookup("#txtTodayFSName");
		TextField txtNameForDel = (TextField) parent.lookup("#txtNameForDel");
		TextField txtContentForDel = (TextField) parent.lookup("#txtContentForDel");
		TextField txtName = (TextField) parent.lookup("#txtName");
		TextField txtContent = (TextField) parent.lookup("#txtContent");

		TableView<FamousSayProperty> tableViewListFS = (TableView<FamousSayProperty>) parent.lookup("#tableViewListFS");
		ObservableList<FamousSayProperty> famousList = (ObservableList<FamousSayProperty>) app.managerFSList();
		TableColumn<FamousSayProperty, Integer> tcListId = new TableColumn<FamousSayProperty, Integer>();
		tcListId.setCellValueFactory(new PropertyValueFactory<FamousSayProperty, Integer>("listIdSim"));
		TableColumn<FamousSayProperty, String> tcName = new TableColumn<FamousSayProperty, String>();
		tcName.setCellValueFactory(new PropertyValueFactory<FamousSayProperty, String>("nameSim"));
		TableColumn<FamousSayProperty, String> tcContent = new TableColumn<FamousSayProperty, String>();
		tcContent.setCellValueFactory(new PropertyValueFactory<FamousSayProperty, String>("contentSim"));
		TableColumn<FamousSayProperty, String> tcUseDate = new TableColumn<FamousSayProperty, String>();
		tcUseDate.setCellValueFactory(new PropertyValueFactory<FamousSayProperty, String>("useDateSim"));
		TableColumn<FamousSayProperty, Integer> tcLiked = new TableColumn<FamousSayProperty, Integer>();
		tcLiked.setCellValueFactory(new PropertyValueFactory<FamousSayProperty, Integer>("likedSim"));
		TableColumn<FamousSayProperty, Integer> tcNoLiked = new TableColumn<FamousSayProperty, Integer>();
		tcNoLiked.setCellValueFactory(new PropertyValueFactory<FamousSayProperty, Integer>("noLikedSim"));

		tcListId.setText("#");
		tcName.setText("이름");
		tcContent.setText("명언");
		tcUseDate.setText("사용날짜");
		tcLiked.setText("좋아요");
		tcNoLiked.setText("싫어요");
		tableViewListFS.getColumns().add(tcListId);
		tableViewListFS.getColumns().add(tcName);
		tableViewListFS.getColumns().add(tcContent);
		tableViewListFS.getColumns().add(tcUseDate);
		tableViewListFS.getColumns().add(tcLiked);
		tableViewListFS.getColumns().add(tcNoLiked);
		tableViewListFS.setItems(famousList);

		txtTodayFSName.setText(famousSay.getName());
		txtTodayFSContent.setText(famousSay.getContent());

		tableViewListFS.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<FamousSayProperty>() {

			@Override
			public void changed(ObservableValue<? extends FamousSayProperty> observable, FamousSayProperty oldValue,
					FamousSayProperty newValue) {
				if (newValue != null) {
					txtNameForDel.setText(newValue.getNameSim());
					txtContentForDel.setText(newValue.getContentSim());
				}
			}
		});

		btnDeleteFS.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (tableViewListFS.getSelectionModel().getSelectedItem() != null) {
					System.out.println(tableViewListFS.getSelectionModel().getSelectedItem().getListIdSim());
				}

			}
		});

		btnAddFS.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				String nameTxt = txtName.getText();
				String contentTxt = txtContent.getText();

				if (nameTxt == null || nameTxt.equals("") || contentTxt == null || contentTxt.equals("")) {
//					messagePopup("정확한 제목과 내용을 입력해주세요!");
				} else {
					app.insertFSForManager(nameTxt, contentTxt);
					tableViewListFS.refresh();
				}

			}
		});

		btnUserManage.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

			}
		});

		btnExit.setOnAction(e -> primaryStage.close());

	}

}
