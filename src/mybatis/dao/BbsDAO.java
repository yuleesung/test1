package mybatis.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import mybatis.service.FactoryService;
import mybatis.vo.BbsVO;
import mybatis.vo.CommentVO;

public class BbsDAO {
	
	// 전체보기
	public static BbsVO[] getList(int begin, int end) {
		BbsVO[] ar = null;
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("begin", begin);
		map.put("end", end);
		
		SqlSession ss = FactoryService.getFactory().openSession();
		List<BbsVO> list = ss.selectList("bbs.getList", map);
		if(list != null && !list.isEmpty()) {
			ar = new BbsVO[list.size()];
			list.toArray(ar);
		}
		ss.close();
		
		return ar;
	}
	
	// 전체 게시물 수
	public static int getCount() {
		SqlSession ss = FactoryService.getFactory().openSession();
		int total = ss.selectOne("bbs.getCount");
		ss.close();
		return total;
	}
	
	// 게시물 추가
	public static boolean addPost(BbsVO vo) {
		boolean chk = false;
		
		SqlSession ss = FactoryService.getFactory().openSession();
		int cnt = ss.insert("bbs.addPost", vo);
		if(cnt > 0) {
			ss.commit();
			chk = true;
		}else {
			ss.rollback();
		}
		ss.close();
		
		return chk;
	}
	
	// 게시물 보기
	public static BbsVO viewPost(String b_idx) {
		SqlSession ss = FactoryService.getFactory().openSession();
		BbsVO vo = ss.selectOne("bbs.viewPost", b_idx);
		ss.close();
		return vo;
	}
	
	// 게시물 수정
	public static boolean editPost(String b_idx, String pwd, String subject, String file_name, String ori_name, String content, String ip) {
		boolean chk = false;
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("b_idx", b_idx);
		map.put("pwd", pwd);
		map.put("subject", subject);
		map.put("file_name", file_name);
		map.put("ori_name", ori_name);
		map.put("content", content);
		map.put("ip", ip);
		
		SqlSession ss = FactoryService.getFactory().openSession();
		int cnt = ss.update("bbs.editPost", map);
		if(cnt > 0) {
			ss.commit();
			chk = true;
		}else {
			ss.rollback();
		}
		ss.close();
		
		return chk;
	}
	
	// 게시물 삭제
	public static boolean delPost(String b_idx, String pwd) {
		boolean chk = false;
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("b_idx", b_idx);
		map.put("pwd", pwd);
		
		SqlSession ss = FactoryService.getFactory().openSession();
		int cnt = ss.update("bbs.delPost", map);
		if(cnt > 0) {
			ss.commit();
			chk = true;
		}else {
			ss.rollback();
		}
		ss.close();
		
		return chk;
	}
	
	// 댓글 추가
	public static boolean addComm(String b_idx, String writer, String content, String pwd, String ip) {
		boolean chk = false;
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("b_idx", b_idx);
		map.put("writer", writer);
		map.put("content", content);
		map.put("pwd", pwd);
		map.put("ip", ip);
		
		SqlSession ss = FactoryService.getFactory().openSession();
		int cnt = ss.insert("bbs.addComm", map);
		if(cnt > 0) {
			ss.commit();
			chk = true;
		}else {
			ss.rollback();
		}
		ss.close();
		
		return chk;
	}
	
	// 조회 수 올리는 기능 - 인자로 받은 b_idx의 게시물 hit를 증가하는 기능
	public static boolean hit(String b_idx) {
		boolean chk = false;
			
		SqlSession ss = FactoryService.getFactory().openSession();
		int cnt = ss.update("bbs.hit", b_idx);
		if(cnt > 0) {
			ss.commit();
			chk = true;
		}else {
			ss.rollback();
		}
		ss.close();
			
		return chk;
	}

}
