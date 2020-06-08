package saying;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//정상완료인지 비정상완료인지에 대한 결과를 팝업으로 알려줘야함
public class ODFSDAO {

	Connection conn = null;
	PreparedStatement pstmt = null;

	public Connection getConnect() { // 세션접속

		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, "hr", "hr");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public List<Member> getMemberList() { // 관리자용 멤버리스트
		List<Member> list = new ArrayList<Member>();
		String sql = "select user_id, login_id, name, email from odfs_users";
		conn = getConnect();
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Member member = new Member();
				member.setUserId(rs.getInt("user_id"));
				member.setLoginId(rs.getString("login_id"));
				member.setName(rs.getString("name"));
				member.setEmail(rs.getString("email"));
				list.add(member);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<FamousSay> getFamousSayingList() { // 관리자용 명언리스트
		List<FamousSay> list = new ArrayList<FamousSay>();
		String sql = "select f.list_id, f.name, f.content, e.liked ,n.noliked "
				+ "from onfs_famous_sayings f join onfs_likecount e" + "on(f.list_id = e.list_id)"
				+ "join onfs_nolikecount n" + "on(f.list_id = n.list_id)";
		conn = getConnect();
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				FamousSay famousSay = new FamousSay();
				famousSay.setName(rs.getString("name"));
				famousSay.setContent(rs.getString("content"));
				famousSay.setLiked(rs.getInt("liked"));
				famousSay.setNoliked(rs.getInt("noliked"));
				list.add(famousSay);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<FamousSay> getLikeList(int userId) { // 사용자용 좋아요 리스트
		List<FamousSay> list = new ArrayList<FamousSay>();
		String sql = String.format("select name, content\n" + "from onfs_famous_sayings\n"
				+ "where list_id in (select list_id from onfs_like\n" + " where user_id = %d)", userId);
		conn = getConnect();
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				FamousSay famousSay = new FamousSay();
				famousSay.setName(rs.getString("name"));
				famousSay.setContent(rs.getString("content"));
				list.add(famousSay);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	public void insertMember(Member member) { // 회원가입
		String sql = String.format("insert into onfs_users values(odfs_user_id_seq.nextVal, '%s', '%s', '%s'. '%s')",
				member.getName(), member.getLoginId(), member.getPassword(), member.getEmail());

		conn = getConnect();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
			// 회원가정상처리된거 반환어케함?
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteFamousSaying(int listId) { // 관리자용 명언삭제
		String sql = String.format("delete onfs_famous_sayings where list_id = %s", listId);
		conn = getConnect();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public Member findMember(String userid, String userpw) { // find user for login
		String sql = String.format(
				"select * from onfs_users where lower(login_id) = lower('%s')" + "and password = '%s'", userid, userpw);
		conn = getConnect();
		Member member = new Member();
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				member.setUserId(rs.getInt("user_id"));
				member.setLoginId(rs.getString("login_id"));
				member.setName(rs.getString("name"));
				member.setEmail(rs.getString("email"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} catch (IndexOutOfBoundsException e1) {
			return null;
		}
		return member;
	}

	// 좋아요 싫어요 취소는 어떻게?
	public void userSelectLikeNoLike(int likeUnlikeSel, int listId, int userId) { // 유저의 좋아요 싫어요 insert
		String sql = null;
		if (likeUnlikeSel == 1) {
			sql = String.format("insert into onfs_like values(%d, %d)", listId, userId);
		} else if (likeUnlikeSel == -1) {
			sql = String.format("insert into onfs_nolike values(%d, %d)", listId, userId);
		} else if (likeUnlikeSel == 2) {
			sql = String.format("delete onfs_like where list_id = %d and user_id = %d", listId, userId);
		} else if (likeUnlikeSel == -2) {
			sql = String.format("delete onfs_nolike where list_id = %d and user_id = %d", listId, userId);
		}
		conn = getConnect();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int getCountFS() {
		String sql1 = "select count(*) as count from onfs_famous_sayings";
		int count = -1;
		conn = getConnect();
		try {
			pstmt = conn.prepareStatement(sql1);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				count = rs.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e1) {

		}
		return count;
	}

	public String getDBtime() {
		String sql = "select sysdate from dual";
		String sysDate = null;
		conn = getConnect();
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				sysDate = rs.getString("sysdate");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e1) {

		}
		return sysDate;
	}

	public FamousSay findFS(int rowNum) {
		String sql = String.format("select list_id, name, content\n"
				+ "from (select rownum as rn, e.* from onfs_famous_sayings e)\n" + "where rn = %s", rowNum);
		FamousSay famousSay = new FamousSay();
		conn = getConnect();
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				famousSay.setListId(rs.getInt("list_id"));
				famousSay.setName(rs.getString("name"));
				famousSay.setContent(rs.getString("content"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e1) {
			e1.printStackTrace();
		}
		return famousSay;
	}

	public boolean findUserLikeIt(int selectTableNum, int list_id, int user_id) {
		String sql = null;
		boolean isInLike = false;
		if (selectTableNum == 1) {
			sql = String.format("select list_id, user_id from onfs_like where list_id = %d and user_id = %d", list_id,
					user_id);
		} else if (selectTableNum == -1) {
			sql = String.format("select list_id, user_id from onfs_nolike where list_id = %d and user_id = %d", list_id,
					user_id);
		}
		conn = getConnect();
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				if (rs.getRow() != 0) {
					isInLike = true;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e1) {
			e1.printStackTrace();
		}
		return isInLike;
	}
	
	
	public List<Integer> getUserLikeItList(int userId) {
		List<Integer> list = new ArrayList<>();
		String sql = String.format("select list_id from onfs_like where user_id = %d", userId);
		conn = getConnect();
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(rs.getInt("list_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public List<Integer> getUserNoLikeItList(int userId) {
		List<Integer> list = new ArrayList<>();
		String sql = String.format("select list_id from onfs_nolike where user_id = %d", userId);
		conn = getConnect();
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(rs.getInt("list_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public List<FamousSayProperty> getFamousSayPropertyList(int userId) {
		List<FamousSayProperty> list = new ArrayList<FamousSayProperty>();
		String sql = String.format("select f.name as name, f.content as content from onfs_famous_sayings f join onfs_like l on(l.list_id = f.list_id) where l.user_id = %s", userId);
		conn = getConnect();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				FamousSayProperty famousSayProperty = new FamousSayProperty(rs.getString("name"), rs.getString("content"));
//				famousSay.setNameSim(rs.getString("name"));
//				famousSay.setContentSim(rs.getString("content"));
				list.add(famousSayProperty);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

}// end of class