package com.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

import com.demo.beans.ContentBean;

public interface BoardMapper {
	
	//content_idx 값이 나오면 먼저(before) sql문을 실행 결과를 입력한다.
	@SelectKey(statement = "select content_seq.nextval from dual", 
			keyProperty = "content_idx", before = true, resultType = int.class)
	
	@Insert("insert into content_table(content_idx, content_subject, content_text, " +
			"content_file, content_writer_idx, content_board_idx, content_date) " +
			"values (#{content_idx}, #{content_subject}, #{content_text}, #{content_file, jdbcType=VARCHAR}, " +
			"#{content_writer_idx}, #{content_board_idx}, sysdate)")
	void addContentInfo(ContentBean writeContentBean);
	
	@Select("select board_info_name from board_info_table "
			+ "where board_info_idx = #{board_info_idx}")
	String getBoardInfoName(int board_info_idx);
	
	@Select("select t1.content_idx, t1.content_subject, t2.user_name as content_writer_name, " + 
			"to_char(t1.content_date, 'YYYY-MM-DD') as content_date " + 
			"from content_table t1 " + 
			"JOIN user_table t2 " + 
			"ON t1.content_writer_idx = t2.user_idx " + 
			"and t1.content_board_idx = #{board_info_idx} order by 1 desc")
	List<ContentBean> getContentList(int board_info_idx, RowBounds rowBounds);

	//게시글 상세 정보 가져오기 
	@Select("select t2.user_name as content_writer_name, " + 
			"to_char(t1.content_date, 'YYYY-MM-DD') as content_date," + 
			"t1.content_subject, t1.content_text, t1.content_file, t1.content_writer_idx " + 
			"from content_table t1 join user_table t2 " + 
			"on t1.content_writer_idx = t2.user_idx " + 
			"and content_idx = #{ ? }")
	ContentBean getContentInfo(int content_idx);
	
	//게시글 업데이트 마이바티스에서 널값이 입력될수도 있을경우 #{변수명, jdbcType=VARCHAR}
	@Update("update content_table "
			+ "set content_subject = #{content_subject}, content_text = #{content_text} , "
			+ "content_file = #{content_file, jdbcType=VARCHAR} "
			+ "where content_idx = #{content_idx}")
	void modifyContentInfo(ContentBean modifyContentBean);
	
	//게시글 번호로 글을 삭제한다
	@Delete("delete from content_table where content_idx=#{content_idx}")
	void deleteContentInfo(int content_idx);

	//게시판종류에 따라서 총 개시물 수
	@Select("select count(*) from content_table where content_board_idx = #{content_board_idx}")	
	int getContentCnt(int content_board_idx);	
}









