package Hobe.Restaurant.Service;

import Hobe.Restaurant.Domain.Booking;
import Hobe.Restaurant.Domain.Table;
import Hobe.Restaurant.Repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public class TableService {
    private final TableRepository tableDB;

    public TableService(TableRepository tableDB) {
        this.tableDB = tableDB;
    }
    public List<Table> showTables(){ //현재 가게에 등록된 테이블 전체 다 보여주는 기능
       return tableDB.findAll();
    }
    public boolean checkTable(long id){ //테이블 번호를 줘서 테이블 데이터베이스에 있는지 확인.
       System.out.println(tableDB.findByID(id).get()) ;
       return true;
    }
//    public boolean checkAvailable(long id){
//        System.out.println("checkAvailable : "+tableDB.findByID(id).get().isAvailable());
//        return tableDB.findByID(id).get().isAvailable();
//    }
//    public void changeAvailable(long id){ //테이블 예약 가능한지 검증한 뒤 또는 예약취소,먹고 갔을 때 해당 메소드 실행
//        Table tempTable = tableDB.findByID(id).get();
//        System.out.println("몇번 테이블 예약?"+tempTable.getTableNumber()+tempTable.isAvailable());
//        if(tempTable.isAvailable()) {
//            tempTable.setAvailable(false);
//            System.out.println(tempTable.isAvailable());
//        }//예약을 했으니 이용가능하지 않다고 설정함.
//        else{
//            tempTable.setAvailable(true); //예약을 취소하거나 시간이 경과한 후.
//        }
//
//        System.out.println(tempTable.isAvailable());
//    }
    public void saveTable(Table table){ //임시용 ...아마도 메인화면 보여질 때 테이블 넣어야될듯.
        tableDB.save(table);
        System.out.println("테이블 등록이 완료되었습니다.");
    }
    public boolean checkMaxNumber(Booking booking,long TableNumber){
        if(booking.getHowMany() > tableDB.findByID(TableNumber).get().getMaxNumber())
            return false;
        return true;
    }

}
