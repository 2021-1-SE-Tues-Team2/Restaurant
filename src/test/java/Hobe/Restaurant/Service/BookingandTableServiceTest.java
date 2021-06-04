package Hobe.Restaurant.Service;

import Hobe.Restaurant.Domain.Booking;
import Hobe.Restaurant.Domain.Member;
import Hobe.Restaurant.Domain.Table;
import Hobe.Restaurant.Repository.BookingMemory;
import Hobe.Restaurant.Repository.BookingRepository;
import Hobe.Restaurant.Repository.MemberMemoryRepository;
import Hobe.Restaurant.Repository.TableMemoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookingandTableServiceTest {
    BookingMemory bookingDB;
    BookingService bookingservice;
    TableService tableservice;
    TableMemoryRepository tableDB;
    MemberService memberservice;
    MemberMemoryRepository memberDB;

    @BeforeEach
    public void beforeEach() {
        memberDB = new MemberMemoryRepository();
        memberservice = new MemberService(memberDB);
        bookingDB = new BookingMemory();
        tableDB = new TableMemoryRepository();
        tableservice = new TableService(tableDB);
        bookingservice = new BookingService(bookingDB, tableservice, memberservice);
    }


    @Test
    void 예약_테스트() {
        Table table = new Table();
        table.setAvailable(true);
        table.setTableNumber(1);
        table.setMaxNumber(10);
        tableservice.saveTable(table);
        Member first = new Member();
        first.setName("first");
        first.setPhone_num("123");
        first.setId(1);
        memberservice.Registraion(first);
        Booking firstborn = new Booking();
        firstborn.setDate("5/27");
        firstborn.setStartTime("13:10");
        firstborn.setEndTime("14:10");
        firstborn.setHowMany(3);
        firstborn.setTableNumber(1);
        bookingservice.reservation(firstborn, first.getId());
    }

    @Test
    void 예약_삭제_테스트() {
        Table table = new Table();
        table.setAvailable(true);
        table.setTableNumber(1);
        table.setMaxNumber(10);
        tableservice.saveTable(table);
        Member delete = new Member();
        delete.setName("delete");
        delete.setPhone_num("123");
        delete.setId(1);
        memberservice.Registraion(delete);
        Booking willdelete = new Booking();
        willdelete.setDate("5/27");
        willdelete.setStartTime("13:10");
        willdelete.setEndTime("14:10");
        willdelete.setHowMany(3);
        willdelete.setTableNumber(1);
        bookingservice.reservation(willdelete, delete.getId());
        bookingservice.Delete_Booking(delete.getId());
    }

    @Test
    void 동일_회원_예약불가능_테스트() {
        Table table = new Table();
        table.setAvailable(true);
        table.setTableNumber(1);
        table.setMaxNumber(10);
        tableservice.saveTable(table);
        Member first = new Member();
        first.setName("first");
        first.setPhone_num("123");
        first.setId(1);
        memberservice.Registraion(first);
        Booking firstborn = new Booking();
        firstborn.setDate("5/27");
        firstborn.setStartTime("13:10");
        firstborn.setEndTime("14:10");
        firstborn.setHowMany(3);
        firstborn.setTableNumber(1);
        bookingservice.reservation(firstborn, first.getId());
        assertThrows(IllegalStateException.class, () -> bookingservice.reservation(firstborn, first.getId()));
    }

    @Test
    void 예약_시간중첩_테이블다름_테스트() {
        Table table = new Table();
        table.setAvailable(true);
        table.setTableNumber(1);
        table.setMaxNumber(10);
        tableservice.saveTable(table);
        Table table2 = new Table();
        table2.setAvailable(true);
        table2.setTableNumber(2);
        table2.setMaxNumber(5);
        tableservice.saveTable(table2);
        Member first = new Member();
        first.setName("first");
        first.setPhone_num("123");
        first.setId(1);
        memberservice.Registraion(first);
        Booking firstborn = new Booking();
        firstborn.setDate("5/27");
        firstborn.setStartTime("13:10");
        firstborn.setEndTime("14:10");
        firstborn.setHowMany(3);
        firstborn.setTableNumber(1);
        bookingservice.reservation(firstborn, first.getId());
        Member second = new Member();
        second.setName("second");
        second.setPhone_num("456");
        second.setId(2);
        memberservice.Registraion(second);
        Booking secondborn = new Booking();
        secondborn.setDate("5/27");
        secondborn.setStartTime("13:10");
        secondborn.setEndTime("14:10");
        secondborn.setHowMany(3);
        secondborn.setTableNumber(2);
        bookingservice.reservation(secondborn, second.getId());
    }

    @Test
    void 예약_시간_테이블중첩_테스트() {
        Table table = new Table();
        table.setAvailable(true);
        table.setTableNumber(1);
        table.setMaxNumber(10);
        tableservice.saveTable(table);
        Member first = new Member();
        first.setName("first");
        first.setPhone_num("123");
        first.setId(1);
        memberservice.Registraion(first);
        Booking firstborn = new Booking();
        firstborn.setDate("5/27");
        firstborn.setStartTime("13:10");
        firstborn.setEndTime("14:10");
        firstborn.setHowMany(3);
        firstborn.setTableNumber(1);
        bookingservice.reservation(firstborn, first.getId());
        Member second = new Member();
        second.setName("second");
        second.setPhone_num("456");
        second.setId(2);
        memberservice.Registraion(second);
        Booking secondborn = new Booking();
        secondborn.setDate("5/27");
        secondborn.setStartTime("13:10");
        secondborn.setEndTime("14:10");
        secondborn.setHowMany(3);
        secondborn.setTableNumber(1);
        assertThrows(IllegalStateException.class, () -> bookingservice.reservation(secondborn, second.getId()));
    }

    void 예약_시간다름_테이블중첩_테스트() {
        Table table = new Table();
        table.setAvailable(true);
        table.setTableNumber(1);
        table.setMaxNumber(10);
        tableservice.saveTable(table);
        Member first = new Member();
        first.setName("first");
        first.setPhone_num("123");
        first.setId(1);
        memberservice.Registraion(first);
        Booking firstborn = new Booking();
        firstborn.setDate("5/27");
        firstborn.setStartTime("13:10");
        firstborn.setEndTime("14:10");
        firstborn.setHowMany(3);
        firstborn.setTableNumber(1);
        bookingservice.reservation(firstborn, first.getId());
        Member second = new Member();
        second.setName("second");
        second.setPhone_num("456");
        second.setId(2);
        memberservice.Registraion(second);
        Booking secondborn = new Booking();
        secondborn.setDate("5/27");
        secondborn.setStartTime("15:50");
        secondborn.setEndTime("17:10");
        secondborn.setHowMany(3);
        secondborn.setTableNumber(1);
        bookingservice.reservation(secondborn, second.getId());
    }

    @Test
    void 예약_동일인물_시간다름_날짜같음_테스트() {
        Table table = new Table();
        table.setAvailable(true);
        table.setTableNumber(1);
        table.setMaxNumber(10);
        tableservice.saveTable(table);
        Member first = new Member();
        first.setName("first");
        first.setPhone_num("123");
        first.setId(1);
        memberservice.Registraion(first);
        Booking firstborn = new Booking();
        firstborn.setDate("5/27");
        firstborn.setStartTime("13:10");
        firstborn.setEndTime("14:10");
        firstborn.setHowMany(3);
        firstborn.setTableNumber(1);
        bookingservice.reservation(firstborn, first.getId());
        firstborn.setDate("5/27");
        firstborn.setStartTime("15:10");
        firstborn.setEndTime("16:10");
        firstborn.setHowMany(3);
        firstborn.setTableNumber(1);
        assertThrows(IllegalStateException.class, () -> bookingservice.reservation(firstborn, first.getId()));
    }

    @Test
    void 예약_시간_날짜같음_테이블다름_테스트() {
        Table table = new Table();
        table.setAvailable(true);
        table.setTableNumber(1);
        table.setMaxNumber(10);
        tableservice.saveTable(table);
        Member first = new Member();
        first.setName("first");
        first.setPhone_num("123");
        first.setId(1);
        memberservice.Registraion(first);
        Booking firstborn = new Booking();
        firstborn.setDate("5/27");
        firstborn.setStartTime("13:10");
        firstborn.setEndTime("14:10");
        firstborn.setHowMany(3);
        firstborn.setTableNumber(1);
        bookingservice.reservation(firstborn, first.getId());
        Member second = new Member();
        second.setName("second");
        second.setPhone_num("456");
        second.setId(2);
        memberservice.Registraion(second);
        Booking secondborn = new Booking();
        secondborn.setDate("5/27");
        secondborn.setStartTime("15:50");
        secondborn.setEndTime("17:10");
        secondborn.setHowMany(3);
        secondborn.setTableNumber(1);
        bookingservice.reservation(secondborn, second.getId());
    }

    @Test
    void 예약시간_변경후_중첩_테스트() {
        Table table = new Table();
        table.setAvailable(true);
        table.setTableNumber(1);
        table.setMaxNumber(10);
        tableservice.saveTable(table);
        Member first = new Member();
        first.setName("first");
        first.setPhone_num("123");
        first.setId(1);
        memberservice.Registraion(first);
        Booking firstborn = new Booking();
        firstborn.setDate("5/31");
        firstborn.setStartTime("13:10");
        firstborn.setEndTime("14:10");
        firstborn.setHowMany(3);
        firstborn.setTableNumber(1);
        bookingservice.reservation(firstborn, first.getId());
        Booking firstchange = new Booking();
        firstchange.setDate("5/31");
        firstchange.setStartTime("15:10");
        firstchange.setEndTime("16:10");
        firstchange.setHowMany(3);
        firstchange.setTableNumber(1);
        bookingservice.ChangeBooking(firstchange,first.getId());
        Member second = new Member();
        second.setName("second");
        second.setPhone_num("456");
        second.setId(2);
        memberservice.Registraion(second);
        Booking secondborn = new Booking();
        secondborn.setDate("5/31");
        secondborn.setStartTime("13:10");
        secondborn.setEndTime("14:10");
        secondborn.setHowMany(3);
        secondborn.setTableNumber(1);
        bookingservice.reservation(secondborn, second.getId());
    }
    @Test
    void 예약변경_시간_중첩_불가능_테스트() {
        Table table = new Table();
        table.setAvailable(true);
        table.setTableNumber(1);
        table.setMaxNumber(10);
        tableservice.saveTable(table);
        Member willchange = new Member();
        willchange.setName("willchange");
        willchange.setPhone_num("123");
        willchange.setId(1);
        memberservice.Registraion(willchange);
        Booking beforechange = new Booking();
        beforechange.setDate("5/31");
        beforechange.setStartTime("13:10");
        beforechange.setEndTime("14:10");
        beforechange.setHowMany(3);
        beforechange.setTableNumber(1);
        bookingservice.reservation(beforechange, willchange.getId());
        Member second = new Member();
        second.setName("second");
        second.setPhone_num("456");
        second.setId(2);
        memberservice.Registraion(second);
        Booking secondborn = new Booking();
        secondborn.setDate("6/1");
        secondborn.setStartTime("13:10");
        secondborn.setEndTime("14:10");
        secondborn.setHowMany(3);
        secondborn.setTableNumber(1);
        bookingservice.reservation(secondborn, second.getId());
        Booking afterchange = new Booking();
        afterchange.setDate("6/1");
        afterchange.setStartTime("13:10");
        afterchange.setEndTime("14:10");
        afterchange.setHowMany(3);
        afterchange.setTableNumber(1);
        assertThrows(IllegalStateException.class, () ->bookingservice.ChangeBooking(afterchange,willchange.getId()));
    }

    @Test
    void 테이블_인원_초과_테스트() {
        Table table = new Table();
        table.setAvailable(true);
        table.setTableNumber(1);
        table.setMaxNumber(5);
        tableservice.saveTable(table);
        Member somany = new Member();
        somany.setName("somany");
        somany.setPhone_num("1234");
        somany.setId(1);
        memberservice.Registraion(somany);
        Booking somanypeople = new Booking();
        somanypeople.setDate("5/31");
        somanypeople.setStartTime("15:50");
        somanypeople.setEndTime("17:10");
        somanypeople.setHowMany(7);
        somanypeople.setTableNumber(1);
        assertThrows(IllegalStateException.class, () -> bookingservice.reservation(somanypeople, somany.getId()));
    }

}