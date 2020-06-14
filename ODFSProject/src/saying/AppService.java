package saying;

import java.util.List;

import javafx.collections.ObservableList;

public interface AppService {
	public Member login(String userId, String userPwd);
	
	public boolean findLikeIt(int likeUnlikeSel, int listId, int userId);
	
	public ObservableList<FamousSayProperty> userLikeList(int userId);
	
	public FamousSay getDailyFS();
	public FamousSay getPreviousFamousSay(int preNum);
	public FamousSay getNextFamousSay(int nextNum);
	
	public List<Integer> getUserLikeList(int selectNum, int userId);
	
	public void insertDeleteUserLikeList(int likeUnlikeSel, int listId, int userId);
	
	public ObservableList<FamousSayProperty> managerFSList();
	
	public void deleteFamousSayingForManager(int listId);
	
	public void insertFSForManager(String name, String fs);
	
	
	
}
