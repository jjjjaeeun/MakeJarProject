package com.itgroup.dao;

import com.itgroup.bean.Member;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//데이터 베이스와 직접 연동하여 CRUD 작업을 수행해주는 DAO 클래스
public class MemberDao {

    public MemberDao() {
        //드라이버 관련 OracleDriver 클래스는 ojdbc6.jar 파일에 포함되어 있는 자바 클래스입니다.
        String driver = "oracle.jdbc.driver.OracleDriver";
        try {
            Class.forName(driver); // 동적 객체 생성하는 문법.

        } catch (ClassNotFoundException e) {

            System.out.println("해당 드라이버가 존재하지 않습니다.");
            throw new RuntimeException(e); // = e.printStackTrace();
        }

    }

    public Connection getConnection() {
        Connection conn = null; //접속 객체

        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String id = "oraman";
        String password = "oracle";

        try {
            conn = DriverManager.getConnection(url, id, password);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return conn;
    }

    public int getSize() {
        String sql = "select count(*) as cnt from members"; // 함수 사용시 as 별칭 적어두기
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection conn = null;
        int cnt = 0;

        try {
            conn = this.getConnection(); // 접속 객체 구현하기
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                cnt = rs.getInt("cnt");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);

        } finally {

            try {

                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }

            } catch (Exception ex) {

                ex.printStackTrace();
            }
        }

        return cnt;
    }

    public List<Member> selectAll() {
        List<Member> members = new ArrayList<Member>();
        //ㄴbean 데이터를 여기에 담아야함
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "select * from members order by name asc";
        ResultSet rs = null;


        try {
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) { // 총수가 불확실한 반복문 while 구문 사용
                //           System.out.println(rs.getString(2));
                //         System.out.println(rs.getInt(7));
                //       System.out.println(rs.getString("id"));
                //     System.out.println(rs.getString("gender"));

                Member bean = new Member();
                bean.setId(rs.getString("id"));
                bean.setName(rs.getString("name"));
                bean.setPassword(rs.getString("password"));
                bean.setGender(rs.getString("gender"));
                bean.setBirth(rs.getString("birth"));
                bean.setMarriage(rs.getString("marriage"));
                bean.setSalary(rs.getInt("salary"));
                bean.setAddress(rs.getString("address"));
                bean.setManager(rs.getString("manager"));

                members.add(bean);
            }

        } catch (Exception ex) {
            ex.printStackTrace();

        } finally {

            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    rs.close();
                }

            } catch (Exception ex) {

            } finally {

            }

        }

        return members;
    }

    public List<Member> findByGender(String gender) {
        // 성별 컬럼 gender을 사용하여 특정 성별의 회원들만 조회합니다.
        String sql = "select * from members where gender = ?";

        List<Member> members = new ArrayList<Member>();

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, gender);
            rs = pstmt.executeQuery();

            while (rs.next()){

                Member bean = new Member();
                bean.setId(rs.getString("id"));
                bean.setName(rs.getString("name"));
                bean.setPassword(rs.getString("password"));
                bean.setGender(rs.getString("gender"));
                bean.setBirth(rs.getString("birth"));
                bean.setMarriage(rs.getString("marriage"));
                bean.setSalary(rs.getInt("salary"));
                bean.setAddress(rs.getString("address"));
                bean.setManager(rs.getString("manager"));

                members.add(bean);

            }

        }catch (Exception ex){

        }finally {
            try {
                if(rs != null){rs.close();}
                if(pstmt != null){pstmt.close();}
                if(conn != null){conn.close();}

            }catch (Exception ex){
                ex.printStackTrace();

            }finally {

            }

        }

        return members;

    }

    public Member getMemberOne(String id) {
        // 로그인 id 정보를 이용하여 해당 사용자의 정보를 bean 형태로 반환해줍니다.
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Member bean = null; // 찾고자 하는 회원 정보

        String sql = "select * from members where id = ?";
        // ? 치환은 executeQuery () 메소드 전에 해야함
        try {
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            // 1번째 ?를 , id로 치환 해
            pstmt.setString(1,id);

            rs = pstmt.executeQuery();

            if(rs.next()){ // 1건 발견됨
                // 반환 하려면 멤버 객체 필요
                bean = new Member();

                bean.setId(rs.getString("id"));
                bean.setName(rs.getString("name"));
                bean.setPassword(rs.getString("password"));
                bean.setGender(rs.getString("gender"));
                bean.setBirth(rs.getString("birth"));
                bean.setMarriage(rs.getString("marriage"));
                bean.setSalary(rs.getInt("salary"));
                bean.setAddress(rs.getString("address"));
                bean.setManager(rs.getString("manager"));
            }

        }catch (Exception ex){
            ex.printStackTrace();

        }finally {

            try {
                if(rs != null){rs.close();}
                if(pstmt != null){pstmt.close();}
                if(conn != null){conn.close();}

            }catch (Exception ex){

            }finally {

            }
        }
        return  bean;
    }

    public int deleteData(String id) { // 기본키를 사용하여 회원 탈퇴를 시도
        int cnt = -1 ;

        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "delete from members where id = ? ";

        try {

            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,id);

            cnt = pstmt.executeUpdate();

            conn.commit();

        }catch (Exception ex){
            try {
                conn.rollback(); // 롤백 구문은 강제로 try catch 해야함

            }catch (Exception ex2){

                ex2.printStackTrace();

            }
            ex.printStackTrace();

        }finally {
            try {

                if(pstmt != null){pstmt.close();}
                if(conn != null){conn.close();}

            }catch (Exception ex){

                ex.printStackTrace();

            }

        }

        return cnt;
    }
}
