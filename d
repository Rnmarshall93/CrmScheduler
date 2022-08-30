[33mcommit eae743cc88c63a691c374d4131cf86ea1e0f13f1[m[33m ([m[1;36mHEAD -> [m[1;32mmaster[m[33m)[m
Author: warpabout <warpabout@gmail.com>
Date:   Thu Dec 2 13:28:33 2021 -0800

    Added customer class

[1mdiff --git a/src/model/Customer.java b/src/model/Customer.java[m
[1mnew file mode 100644[m
[1mindex 0000000..f90839f[m
[1m--- /dev/null[m
[1m+++ b/src/model/Customer.java[m
[36m@@ -0,0 +1,97 @@[m
[32m+[m[32mpackage model;[m
[32m+[m
[32m+[m[32mimport java.util.Date;[m
[32m+[m
[32m+[m[32mpublic class Customer {[m
[32m+[m
[32m+[m[32m    private int customerId;[m
[32m+[m[32m    private String customerName;[m
[32m+[m[32m    private String address;[m
[32m+[m[32m    private String postalCode;[m
[32m+[m[32m    private String phone;[m
[32m+[m[32m    private Date createDate;[m
[32m+[m[32m    private String createdBy;[m
[32m+[m[32m    private Date lastUpdate;[m
[32m+[m[32m    private String lastUpdatedBy;[m
[32m+[m[32m    private int divisionId;[m
[32m+[m
[32m+[m[32m    public int getCustomerId() {[m
[32m+[m[32m        return customerId;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public void setCustomerId(int customerId) {[m
[32m+[m[32m        this.customerId = customerId;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public String getCustomerName() {[m
[32m+[m[32m        return customerName;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public void setCustomerName(String customerName) {[m
[32m+[m[32m        this.customerName = customerName;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public String getAddress() {[m
[32m+[m[32m        return address;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public void setAddress(String address) {[m
[32m+[m[32m        this.address = address;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public String getPostalCode() {[m
[32m+[m[32m        return postalCode;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public void setPostalCode(String postalCode) {[m
[32m+[m[32m        this.postalCode = postalCode;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public String getPhone() {[m
[32m+[m[32m        return phone;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public void setPhone(String phone) {[m
[32m+[m[32m        this.phone = phone;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public Date getCreateDate() {[m
[32m+[m[32m        return createDate;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public void setCreateDate(Date createDate) {[m
[32m+[m[32m        this.createDate = createDate;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public String getCreatedBy() {[m
[32m+[m[32m        return createdBy;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public void setCreatedBy(String createdBy) {[m
[32m+[m[32m        this.createdBy = createdBy;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public Date getLastUpdate() {[m
[32m+[m[32m        return lastUpdate;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public void setLastUpdate(Date lastUpdate) {[m
[32m+[m[32m        this.lastUpdate = lastUpdate;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public String getLastUpdatedBy() {[m
[32m+[m[32m        return lastUpdatedBy;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public void setLastUpdatedBy(String lastUpdatedBy) {[m
[32m+[m[32m        this.lastUpdatedBy = lastUpdatedBy;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public int getDivisionId() {[m
[32m+[m[32m        return divisionId;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public void setDivisionId(int divisionId) {[m
[32m+[m[32m        this.divisionId = divisionId;[m
[32m+[m[32m    }[m
[32m+[m[32m}[m
