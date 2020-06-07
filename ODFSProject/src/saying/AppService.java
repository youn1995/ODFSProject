package saying;

import java.util.List;

import javafx.collections.ObservableList;

public interface AppService {
// 버튼과 연결작업하기 위한 인터페이스
	public Member login(String userId, String userPwd);
	
	public boolean findLikeIt(int likeUnlikeSel, int listId, int userId);
	
	public void next();
	
	public List<FamousSayProperty> userLikeList(int userId);
	
	public FamousSay getDailyFS();
	
	public List<Integer> getUserLikeList(int selectNum, int userId);
	
	public void insertDeleteUserLikeList(int likeUnlikeSel, int listId, int userId);
}
