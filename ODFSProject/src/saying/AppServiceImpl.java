package saying;

import java.util.List;
import java.util.Random;

import javafx.collections.ObservableList;

public class AppServiceImpl implements AppService {

	@Override
	public Member login(String userId, String userPwd) {
		String userInputId = userId.trim();
		String userInputPwd = userPwd.trim();

		ODFSDAO dao = new ODFSDAO();
		return dao.findMember(userInputId, userInputPwd);
	}

	@Override
	public boolean findLikeIt(int likeUnlikeSel, int listId, int userId) {
		ODFSDAO dao  = new ODFSDAO();
		return dao.findUserLikeIt(likeUnlikeSel, listId, userId);

	}

	@Override
	public void next() {
		// TODO Auto-generated method stub

	}

	@Override
	public FamousSay getDailyFS() {
		ODFSDAO dao = new ODFSDAO();
		String sysDate = dao.getDBtime();
		int count = dao.getCountFS();
		int Date = Integer.parseInt(sysDate.trim().substring(0,10).replace("-", ""));
		Random random = new Random(Date);
		int rowFS = random.nextInt(count)+1;
		FamousSay fs = dao.findFS(rowFS);
		
		return fs;
	}

	@Override
	public List<Integer> getUserLikeList(int selectNum, int userId) {
		ODFSDAO dao = new ODFSDAO();
		if(selectNum == 1) {
			return dao.getUserLikeItList(userId);
		} else if(selectNum == -1) {
			return dao.getUserNoLikeItList(userId);
		}
		return null;
	}

	@Override
	public void insertDeleteUserLikeList(int likeUnlikeSel, int listId, int userId) {
		ODFSDAO dao = new ODFSDAO();
		dao.userSelectLikeNoLike(likeUnlikeSel, listId, userId);
	}

	@Override
	public ObservableList<FamousSayProperty> userLikeList(int userId) {

		ODFSDAO dao = new ODFSDAO();
		
		ObservableList<FamousSayProperty> list = dao.getFamousSayPropertyList(userId);
		return list;
		
		 
	}

}
