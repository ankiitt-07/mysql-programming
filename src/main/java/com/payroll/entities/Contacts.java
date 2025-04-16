package com.payroll.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Contacts {
    int contact_id;
    String phone_number;
    String email;
    String address;
    int employee_id;

    public Contacts(int contact_id, String phone_number, String email, String address, int employee_id) {
        this.contact_id = contact_id;
        this.phone_number = phone_number;
        this.email = email;
        this.address = address;
        this.employee_id = employee_id;
    }

    @Override
    public String toString() {
        return "[contact_id=" + contact_id + ", phone_number=" + phone_number + "]";
    }
}
