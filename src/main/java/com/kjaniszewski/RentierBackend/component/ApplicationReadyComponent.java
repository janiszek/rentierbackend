package com.kjaniszewski.RentierBackend.component;

import com.kjaniszewski.RentierBackend.entity.*;
import com.kjaniszewski.RentierBackend.enums.BillType;
import com.kjaniszewski.RentierBackend.enums.PaymentType;
import com.kjaniszewski.RentierBackend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

@Component
public class ApplicationReadyComponent {

    private final LocationRepository locationRepo;
    private final TenantRepository tenantRepo;
    private final ContractRepository contractRepo;
    private final PaymentRepository paymentRepo;
    private final BillGroupRepository billGroupRepo;
    private final BillRepository billRepo;

    @Autowired
    public ApplicationReadyComponent(LocationRepository locationRepo, TenantRepository tenantRepo,
                                     ContractRepository contractRepo, PaymentRepository paymentRepo,
                                     BillGroupRepository billGroupRepo, BillRepository billRepo) {
        this.locationRepo = locationRepo;
        this.tenantRepo = tenantRepo;
        this.contractRepo = contractRepo;
        this.paymentRepo = paymentRepo;
        this.billGroupRepo = billGroupRepo;
        this.billRepo = billRepo;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void ApplicationReadyExecute() {
        if (locationRepo.findAll().size()==0) {
            System.out.println("Starting Rentier Backend Application initalization");
		    InitializeData();
		    CheckData();
            System.out.println("Ending Rentier Backend Application initalization");
        }
        System.out.println("Rentier Backend Application ready to use...");
    }

    private void InitializeData(){
        InitializeLocations();
        InitializeTenants();
        InitializeContracts();
        InitializePayments();
        InitializeBillGroups();
        InitializeBills();
    }

    private void InitializeLocations(){
        System.out.println("Adding 4 locations...");
        /*Location location1 = new Location("Kromera", "Wroclaw, ul. Kromera 53/17","kromera.jpg");
        Location location2 = new Location("Stronska", "Wroclaw, ul. Stronska 2B/28","stronska.jpg");
        Location location3 = new Location("Kurkowa", "Wroclaw, ul. Kurkowa 14/98","kurkowa.jpg");
        Location location4 = new Location( "Zakladowa", "Wroclaw, ul. Zakladowa 28/7","zakladowa.jpg");
        locationRepo.save(location1);
        locationRepo.save(location2);
        locationRepo.save(location3);
        locationRepo.save(location4);*/

        //stream version applied
        List<Location> locationList = Arrays.asList(
            new Location("Kromera", "Wroclaw, ul. Kromera 53/17","kromera.jpg"),
            new Location("Stronska", "Wroclaw, ul. Stronska 2B/28","stronska.jpg"),
            new Location("Kurkowa", "Wroclaw, ul. Kurkowa 14/98","kurkowa.jpg"),
            new Location( "Zakladowa", "Wroclaw, ul. Zakladowa 28/7","zakladowa.jpg")
        );
        //shorter  way of description with ::
        locationList.stream().forEach(locationRepo::save);
    }

    private void InitializeTenants(){
        System.out.println("Adding 3 tenants...");
        /*Tenant tenant1 = new Tenant("Forys Emil i Kaja");
        TenantDetails tenDet1 = new TenantDetails("Emil","Forys","123456789","ABC123456","Kozia Wolka 31, Polska","forys@gmail.com", "123 456", "To tylko fakeowy tenant");
        tenant1.setDetails(tenDet1);
        Tenant tenant2 = new Tenant("Cieslak Rafal i Elżbieta");
        TenantDetails tenDet2 = new TenantDetails("Rafal","Cieslak","1111111111","ABC123456","Bidbulowo 12, Polska","cieslak@gmail.com", "456 789", "To tylko fakeowy tenant");
        tenant2.setDetails(tenDet2);
        Tenant tenant3 = new Tenant("Cienciala Szymon");
        TenantDetails tenDet3 = new TenantDetails("Szymon","Cienciala","987654321","ABC123456","Nijako 63/2a, Polska","cienciala@gmail.com", "789 123", "To tylko fakeowy tenant");
        tenant3.setDetails(tenDet3);
        tenantRepo.save(tenant1);
        tenantRepo.save(tenant2);
        tenantRepo.save(tenant3);*/

        //stream version applied
        TenantDetails tenDet1 = new TenantDetails("Emil","Forys","123456789","ABC123456","Kozia Wolka 31, Polska","forys@gmail.com", "123 456", "To tylko fakeowy tenant");
        TenantDetails tenDet2 = new TenantDetails("Rafal","Cieslak","1111111111","ABC123456","Bidbulowo 12, Polska","cieslak@gmail.com", "456 789", "To tylko fakeowy tenant");
        TenantDetails tenDet3 = new TenantDetails("Szymon","Cienciala","987654321","ABC123456","Nijako 63/2a, Polska","cienciala@gmail.com", "789 123", "To tylko fakeowy tenant");
        List<Tenant> tenantList = Arrays.asList(
                new Tenant("Forys Emil i Kaja", tenDet1),
                new Tenant("Cieslak Rafal i Elżbieta", tenDet2),
                new Tenant("Cienciala Szymon", tenDet3)
        );
        tenantList.stream().forEach(tenantRepo::save);
    }

    private void InitializeContracts(){
        System.out.println("Adding 5 contracts...");

        Location loc1 = locationRepo.findById(1L).orElse(null);
        Location loc2 = locationRepo.findById(2L).orElse(null);
        Location loc4 = locationRepo.findById(4L).orElse(null);

        Tenant ten1 = tenantRepo.findById(1L).orElse(null);
        Tenant ten2 = tenantRepo.findById(2L).orElse(null);
        Tenant ten3 = tenantRepo.findById(3L).orElse(null);

        Contract contract1 = new Contract(Date.valueOf("2018-11-03"), Date.valueOf("2018-11-01"), Date.valueOf("2019-10-31"));
        contract1.setLocation(loc1);
        contract1.setTenant(ten1);
        Contract contract2 = new Contract(Date.valueOf("2019-10-05"), Date.valueOf("2019-11-01"), Date.valueOf("2020-10-31"));
        contract2.setLocation(loc1);
        contract2.setTenant(ten1);
        Contract contract3 = new Contract(Date.valueOf("2019-03-31"), Date.valueOf("2019-05-01"), Date.valueOf("2020-04-30"));
        contract3.setLocation(loc2);
        contract3.setTenant(ten2);
        Contract contract4 = new Contract(Date.valueOf("2020-04-02"), Date.valueOf("2020-05-01"), Date.valueOf("2021-04-30"));
        contract4.setLocation(loc2);
        contract4.setTenant(ten2);
        Contract contract5 = new Contract(Date.valueOf("2020-03-25"), Date.valueOf("2020-03-25"), Date.valueOf("2021-03-31"));
        contract5.setLocation(loc4);
        contract5.setTenant(ten3);

        contractRepo.save(contract1);
        contractRepo.save(contract2);
        contractRepo.save(contract3);
        contractRepo.save(contract4);
        contractRepo.save(contract5);
    }

    private void InitializePayments(){
        System.out.println("Adding payments for location 1 and tenant 1...");
        Location loc1 = locationRepo.findById(1L).orElse(null);
        Location loc2 = locationRepo.findById(2L).orElse(null);

        Tenant ten1 = tenantRepo.findById(1L).orElse(null);
        Tenant ten2 = tenantRepo.findById(2L).orElse(null);

        Payment payment1 = new Payment(Date.valueOf("2020-05-01"), new Float(400.0), PaymentType.CHARGED);
        payment1.setLocation(loc1);
        payment1.setTenant(ten1);
        Payment payment2 = new Payment(Date.valueOf("2020-06-01"), new Float(400.0), PaymentType.FORECAST);
        payment2.setLocation(loc1);
        payment2.setTenant(ten1);
        Payment payment3 = new Payment(Date.valueOf("2020-07-01"), new Float(400.0), PaymentType.FORECAST);
        payment3.setLocation(loc1);
        payment3.setTenant(ten1);
        Payment payment4 = new Payment(Date.valueOf("2020-05-01"), new Float(350.0), PaymentType.CHARGED);
        payment4.setLocation(loc2);
        payment4.setTenant(ten2);
        Payment payment5 = new Payment(Date.valueOf("2020-06-01"), new Float(750.0), PaymentType.FORECAST);
        payment5.setLocation(loc2);
        payment5.setTenant(ten2);
        Payment payment6 = new Payment(Date.valueOf("2020-07-01"), new Float(450.0), PaymentType.FORECAST);
        payment6.setLocation(loc2);
        payment6.setTenant(ten2);

        paymentRepo.save(payment1);
        paymentRepo.save(payment2);
        paymentRepo.save(payment3);
        paymentRepo.save(payment4);
        paymentRepo.save(payment5);
        paymentRepo.save(payment6);
    }

    private void InitializeBillGroups(){
        System.out.println("Adding bill groups...");
        /*BillGroup group1 = new BillGroup("Community fees", true);
        BillGroup group2 = new BillGroup("Internet", true);
        BillGroup group3 = new BillGroup( "electricity", true);
        BillGroup group4 = new BillGroup("insurance", true);
        BillGroup group5 = new BillGroup("meters settlement", false);
        BillGroup group6 = new BillGroup("power settlement", false);
        BillGroup group7 = new BillGroup("other", false);

        billGroupRepo.save(group1);
        billGroupRepo.save(group2);
        billGroupRepo.save(group3);
        billGroupRepo.save(group4);
        billGroupRepo.save(group5);
        billGroupRepo.save(group6);
        billGroupRepo.save(group7);*/

        //stream version applied
        List<BillGroup> billGroupList = Arrays.asList(
                new BillGroup("community fees", true),
                new BillGroup("internet", true),
                new BillGroup( "electricity", true),
                new BillGroup("insurance", true),
                new BillGroup("meters settlement", false),
                new BillGroup("power settlement", false),
                new BillGroup("other", false)
        );
        //longer way of description
        billGroupList.stream().forEach(group -> billGroupRepo.save(group));
    }

    private void InitializeBills(){
        System.out.println("Adding bills...");
        Bill bill1 = new Bill(Date.valueOf("2020-05-01"), new Float(306.73), BillType.PAID);
        Location loc1 = locationRepo.findById(1L).orElse(null);
        BillGroup group1 = billGroupRepo.findById(1L).orElse(null);
        bill1.setLocation(loc1);
        bill1.setBillGroup(group1);
        Bill bill2 = new Bill(Date.valueOf("2020-05-01"), new Float(63.33), BillType.PAID);
        BillGroup group3 = billGroupRepo.findById(3L).orElse(null);
        bill2.setLocation(loc1);
        bill2.setBillGroup(group3);
        Bill bill3 = new Bill(Date.valueOf("2020-06-01"), new Float(268.32), BillType.REAL);
        bill3.setLocation(loc1);
        bill3.setBillGroup(group1);

        Bill bill4 = new Bill(Date.valueOf("2020-05-01"), new Float(322.01), BillType.PAID);
        Location loc2 = locationRepo.findById(2L).orElse(null);
        bill4.setLocation(loc2);
        bill4.setBillGroup(group1);
        Bill bill5 = new Bill(Date.valueOf("2020-05-01"), new Float(19.46), BillType.PAID);
        BillGroup group4 = billGroupRepo.findById(4L).orElse(null);
        bill5.setLocation(loc2);
        bill5.setBillGroup(group4);

        Bill bill6 = new Bill(Date.valueOf("2020-05-01"), new Float(256.33), BillType.PAID);
        Location loc3 = locationRepo.findById(3L).orElse(null);
        bill6.setLocation(loc3);
        bill6.setBillGroup(group1);

        Bill bill7 = new Bill(Date.valueOf("2020-05-01"), new Float(19.38), BillType.PAID);
        bill7.setLocation(loc1);
        bill7.setBillGroup(group4);
        Bill bill8 = new Bill(Date.valueOf("2020-06-01"), new Float(63.33), BillType.REAL);
        bill8.setLocation(loc1);
        bill8.setBillGroup(group3);
        Bill bill9 = new Bill(Date.valueOf("2020-06-01"), new Float(19.38), BillType.REAL);
        bill9.setLocation(loc1);
        bill9.setBillGroup(group4);
        Bill bill10 = new Bill(Date.valueOf("2020-07-01"), new Float(268.32), BillType.REAL);
        bill10.setLocation(loc1);
        bill10.setBillGroup(group1);
        Bill bill11 = new Bill(Date.valueOf("2020-07-01"), new Float(63.33), BillType.REAL);
        bill11.setLocation(loc1);
        bill11.setBillGroup(group3);
        Bill bill12 = new Bill(Date.valueOf("2020-07-01"), new Float(19.38), BillType.REAL);
        bill12.setLocation(loc1);
        bill12.setBillGroup(group4);
        Bill bill13 = new Bill(Date.valueOf("2020-08-01"), new Float(268.32), BillType.FORECAST);
        bill13.setLocation(loc1);
        bill13.setBillGroup(group1);
        Bill bill14 = new Bill(Date.valueOf("2020-08-01"), new Float(63.33), BillType.FORECAST);
        bill14.setLocation(loc1);
        bill14.setBillGroup(group3);
        Bill bill15 = new Bill(Date.valueOf("2020-08-01"), new Float(19.38), BillType.FORECAST);
        bill15.setLocation(loc1);
        bill15.setBillGroup(group4);

        billRepo.save(bill1);
        billRepo.save(bill2);
        billRepo.save(bill3);
        billRepo.save(bill4);
        billRepo.save(bill5);
        billRepo.save(bill6);
        billRepo.save(bill7);
        billRepo.save(bill8);
        billRepo.save(bill9);
        billRepo.save(bill10);
        billRepo.save(bill11);
        billRepo.save(bill12);
        billRepo.save(bill13);
        billRepo.save(bill14);
        billRepo.save(bill15);
    }


    private void CheckData(){
        List<Location> locationList = locationRepo.findAll();
        System.out.println("Show all locations: "+locationList);

        List<Tenant> tenantList = tenantRepo.findAll();
        System.out.println("Show all tenants: "+tenantList);

        List<Contract> contractList = contractRepo.findAll();
        System.out.println("Show all contracts: "+contractList);

        List<Payment> paymentList = paymentRepo.findAll();
        System.out.println("Show all payments: "+paymentList);

        List<BillGroup> billGroupList = billGroupRepo.findAll();
        System.out.println("Show all bill groups: "+billGroupList);

        List<Bill> billList = billRepo.findAll();
        System.out.println("Show all bills: "+billList);

    }

}
