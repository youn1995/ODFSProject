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
		ODFSDAO dao = new ODFSDAO();
		return dao.findUserLikeIt(likeUnlikeSel, listId, userId);

	}

	@Override
	public FamousSay getDailyFS() {
		ODFSDAO dao = new ODFSDAO();
		FamousSay fs = dao.findFS();

		return fs;
	}

	@Override
	public List<Integer> getUserLikeList(int selectNum, int userId) {
		ODFSDAO dao = new ODFSDAO();
		if (selectNum == 1) {
			return dao.getUserLikeItList(userId);
		} else if (selectNum == -1) {
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

	@Override
	public ObservableList<FamousSayProperty> managerFSList() {
		ODFSDAO dao = new ODFSDAO();
		ObservableList<FamousSayProperty> list = dao.getFamousSayingList();

		return list;
	}

	@Override
	public void deleteFamousSayingForManager(int listId) {
		ODFSDAO dao = new ODFSDAO();
		dao.deleteFamousSaying(listId);
	}

	@Override
	public void insertFSForManager(String name, String fs) {
		ODFSDAO dao = new ODFSDAO();
		dao.insertFamousSayForManager(name, fs);
	}

	@Override
	public FamousSay getPreviousFamousSay(int preNum) {
		ODFSDAO dao = new ODFSDAO();
		FamousSay returnPreFS = dao.getPreviousFS(preNum);
		return returnPreFS;
	}

	@Override
	public FamousSay getNextFamousSay(int nextNum) {
		ODFSDAO dao = new ODFSDAO();
		FamousSay returnNextFS = dao.getNextFS(nextNum);
		return returnNextFS;
	}

}
