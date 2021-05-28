package Hobe.Restaurant.Repository;

import Hobe.Restaurant.Domain.Admin;

public interface AdminRepository {
    Admin save(Admin admin);
    Admin output();
    void UpdateInfo(Admin admin);
}
