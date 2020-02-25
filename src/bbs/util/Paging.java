package bbs.util;

public class Paging {
	
	int nowPage = 1;
	int numPerPage = 10;
	int totalRecord = 0;
	int pagePerBlock = 3;
	int totalPage = 0;
	
	int begin, end, startPage, endPage;
	
	public Paging() {} // 기본생성자
	
	public Paging(int numPerPage, int pagePerBlock) { // 보여질 게시물 수를 바꾸고 싶으면 이 생성자를 쓰면 된다.
		super();
		this.numPerPage = numPerPage;
		this.pagePerBlock = pagePerBlock;
	}

	public int getNowPage() {
		return nowPage;
	}

	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
		if(nowPage > totalPage)
			nowPage = totalPage;
		
		begin = (nowPage-1)*numPerPage+1;
		end = (begin+numPerPage)-1;
		
		startPage = (nowPage-1)/pagePerBlock*pagePerBlock+1;
		endPage = startPage+pagePerBlock-1;

		if(endPage > totalPage)
			endPage = totalPage;
	}

	public int getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
		
		// 총 게시물에 의해서 총 페이지 수가 결정된다.
		this.totalPage = (int) Math.ceil((double)totalRecord/numPerPage);
	}

	public int getPagePerBlock() {
		return pagePerBlock;
	}

	public void setPagePerBlock(int pagePerBlock) {
		this.pagePerBlock = pagePerBlock;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getBegin() {
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	
	

}
