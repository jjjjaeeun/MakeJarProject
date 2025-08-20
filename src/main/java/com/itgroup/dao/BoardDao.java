package com.itgroup.dao;

import com.itgroup.bean.Board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoardDao extends SuperDao{

    public BoardDao(){

    }

    public List<Board> selectAll() {
        // 전체 게시물을 최신 항목부터 조회하여 반환합니다.

        List<Board> boardList = new ArrayList<Board>();

        Connection conn = null ;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql ="select * from boards order by no desc";


        try {
            conn = super.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()){
                Board bean = this.makeBean(rs);

                boardList.add(bean);

            }

        }catch (Exception e){
            e.printStackTrace();

        }finally {

            try {
                if (rs != null){rs.close();}
                if (pstmt != null){pstmt.close();}
                if (conn != null){conn.close();}

            }catch (Exception e){
                e.printStackTrace();

            }

        }

        return boardList;
    }

    private Board makeBean(ResultSet rs) {
        // ResultSet 에서 데이터를 읽어와서 Bean 객체에 담아 반환함
        Board bean = null;
        try {
            bean = new Board();
            bean.setNo(rs.getInt("no"));
            bean.setWriter(rs.getNString("writer"));
            bean.setPassword(rs.getNString("password"));
            bean.setSubject(rs.getNString("subject"));
            bean.setContent(rs.getNString("content"));
            bean.setReadhit(rs.getNString("readhit"));
            bean.setRegdate(rs.getNString("regdate"));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bean;
    }

    public List<Board> selectEvenData() {
        String sql = "select * from boards where mod(no, 2) = 0 order by no desc";
        List<Board> boardList = new ArrayList<Board>();

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = super.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()){
                Board bean = this.makeBean(rs);

                boardList.add(bean);
            }

        }catch (Exception e){
            e.printStackTrace();

        }finally {
            try {
                if(rs != null){rs.close();}
                if (pstmt != null){rs.close();}
                if (conn != null){conn.close();}

            }catch (Exception e){
                e.printStackTrace();

            }

        }

        return null;
    }
}
