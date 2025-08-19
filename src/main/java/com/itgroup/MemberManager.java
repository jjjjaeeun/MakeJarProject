package com.itgroup;

import com.itgroup.bean.Member;
import com.itgroup.dao.MemberDao;

import java.util.List;

// 메인 클래스 대신 실제 모든 업무를 총 책임지는 매니저 클래스
public class MemberManager {
    private MemberDao dao = null; // 실제 데이터 베이스와 연동하는 다오 클래스

    public MemberManager() {
        this.dao = new MemberDao();
    }

    public void selectAll() { //모든 회원 정보 조회
        //반환 타입을 포함한 리스트 컬랙션 사용
        List<Member> members = dao.selectAll();

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

        int cnt = dao.getSize();
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
        List<Member> mydata = dao.findByGender(gender);

        System.out.println("이름 "+ "성별");

        for (Member bean : mydata){

            String name = bean.getName();

            System.out.println(name+"\t"+gender);
        }

    }

    public void getMemberOne() {

        String findId = "xx"; // 찾고자 하는 회원

        //반환타입 객체 = dao.메소드이름(매개변수);
        Member someone = dao.getMemberOne(findId);

        if(someone == null){
            System.out.println("찾으시는 회원이 존재하지 않습니다.");

        }else {

            String id = someone.getId();
            String name = someone.getName();
            String gender = someone.getGender();
            String message = id + "\t" + name + "\t" + gender;
            System.out.println(message);
        }
    }

    public void deleteData() { // 나의 id를 사용한 탈퇴
        String id = "yusin";
        int cnt = -1 ;
         cnt = dao.deleteData(id);
        if(cnt == -1){
            System.out.println("회원 탈퇴 실패(접속, 네트워크 오류)");
        }else if(cnt == 0){
            System.out.println("탈퇴할 회원이 존재하지 않습니다.");
        }else if(cnt > 0){
            System.out.println("회원 탈퇴 완료");
        }else {

        }
    }
}
