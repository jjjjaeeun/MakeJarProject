package com.itgroup;

import com.itgroup.bean.Board;
import com.itgroup.bean.Member;
import com.itgroup.dao.BoardDao;
import com.itgroup.dao.MemberDao;

import java.util.List;
import java.util.Scanner;

// 메인 클래스 대신 실제 모든 업무를 총 책임지는 매니저 클래스
public class DataManager {
    private MemberDao mdao = null; // 실제 데이터 베이스와 연동하는 다오 클래스
    private BoardDao bdao = null;
    private Scanner scan = null; // 회원 정보 입력 받기 위한 스캐너 장치

    public DataManager() {
        this.mdao = new MemberDao();
        this.scan = new Scanner(System.in);
        this.bdao = new BoardDao();
    }

    public void selectAll() { //모든 회원 정보 조회
        //반환 타입을 포함한 리스트 컬랙션 사용
        List<Member> members = mdao.selectAll();

        // 컬렉션은 확장포 사용
        System.out.println("이름\t급여\t주소");
        for (Member bean : members) {
            String name = bean.getName();
            int salary = bean.getSalary();
            String address = bean.getAddress();
            String message = name + "\t" + salary + "\t" + address;
            System.out.println(message);


        }

    }

    public void getSize() { //몇명의 회원인지 조회 하는 기능

        int cnt = mdao.getSize();
        String message;

        if (cnt == 0) {
            message = "검색된 회원이 존재하지 않습니다.";
        } else {
            message = "검색된 회원은 총 " + cnt + "명 입니다.";
        }
        System.out.println(message);

    }

    public void findByGender() {
        String gender = "여자";
        List<Member> mydata = mdao.findByGender(gender);

        System.out.println("이름 " + "성별");

        for (Member bean : mydata) {

            String name = bean.getName();

            System.out.println(name + "\t" + gender);
        }

    }

    public void getMemberOne() {

        String findId = "yusin"; // 찾고자 하는 회원

        //반환타입 객체 = mdao.메소드이름(매개변수);
        Member someone = mdao.getMemberOne(findId);

        if (someone == null) {
            System.out.println("찾으시는 회원이 존재하지 않습니다.");

        } else {

            String id = someone.getId();
            String name = someone.getName();
            String gender = someone.getGender();
            String message = id + "\t" + name + "\t" + gender;
            System.out.println(message);
        }
    }

    public void deleteData() { // 나의 id를 사용한 탈퇴
        String id = "yusin";
        int cnt = -1;
        cnt = mdao.deleteData(id);
        if (cnt == -1) {
            System.out.println("회원 탈퇴 실패(접속, 네트워크 오류)");
        } else if (cnt == 0) {
            System.out.println("탈퇴할 회원이 존재하지 않습니다.");
        } else if (cnt > 0) {
            System.out.println("회원 탈퇴 완료");
        } else {

        }
    }

    public void insertData() {
        //  cnt의 의미 insert : 추가된 행수(1건)
        // dml 반환 타입은 int
        int cnt = -1;
        Member bean = new Member();

        System.out.print("id 입력: ");
        String id = scan.next();

        System.out.print("이름 입력: ");
        String name = scan.next();

        // 입력 받을 값 할당
        bean.setId(id);
        bean.setName(name);
        bean.setPassword("abc123");
        bean.setGender("남자");
        bean.setBirth("25/08/20");
        bean.setMarriage("결혼");
        bean.setSalary(100);
        bean.setAddress("서대문");
        bean.setManager(null);

        cnt = mdao.insertData(bean);

        if(cnt == -1){
            System.out.println("회원 가입 실패");

        }else if (cnt == 1){
            System.out.println("회원 id: "+ id + "로 가입 성공");
        }else {

        }
    }

    public void updateData() {
        int cnt = -1 ;

        System.out.print("수정하고자 하는 회원 id 입력: ");
        String findId = scan.next(); // yusin 입력

        // bean은 과거에 입력했던 데이터 정보
        Member bean = mdao.getMemberOne(findId);

        //이름과 결혼 여부를 변경
        System.out.print("이름 입력: ");
        String name = scan.next();

        System.out.print("결혼 여부 입력: ");
        String marriage = scan.next();

        bean.setName(name);
        bean.setMarriage(marriage);

        cnt = mdao.updateData(bean);

        if(cnt == -1){
            System.out.println("업데이트 실패");
        }else if (cnt == 1){
            System.out.println("업데이트 성공");
        }else {

        }
    }

    public void selectAllBoard() {
        List<Board> boardList = bdao.selectAll();

        System.out.println("글번호 작성자 글제목 글내용");
        for(Board bean : boardList){
            int no = bean.getNo();
            String writer = bean.getWriter();
            String subject = bean.getSubject();
            String content = bean.getContent();
            String message = no + "\t" + writer+"\t"+subject+"\t"+content;
            System.out.println(message);

        }
    }

    public void selectEvenBoard() {
        // 게시물 번호가 짝수인 항목들만 조회
        List<Board> boardList = bdao.selectEvenData();

        System.out.println("글번호 작성자 글제목 글내용");
        for(Board bean : boardList){
            int no = bean.getNo();
            String writer = bean.getWriter();
            String subject = bean.getSubject();
            String content = bean.getContent();
            String message = no + "\t" + writer+"\t"+subject+"\t"+content;
            System.out.println(message);

    }
}
}
