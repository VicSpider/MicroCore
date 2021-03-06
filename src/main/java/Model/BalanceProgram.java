package Model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 账户余额在空间维度的细分
 * Created by Victor on 2018/10/21.
 */
public class BalanceProgram {
    Account account;         //该BP所属账户
    String assetAttr = "";   //资产属性
    String productAttr = ""; //业务属性
    int waive = 0;           //生息标识,若为1则该BP下的所有余额不生息，若为2则免收当期利息
    boolean freeInt;         //该BP的余额是否包含免息期
    /**
     * 账户余额List分两个栏位
     * 0: Balance
     * 1: FEE
     * 每个List由若干不同利率的余额链构成
     */
    List<List<BalanceList>> balance;

    public BalanceProgram(Account account) {
        this.account = account;
        balance = new ArrayList<List<BalanceList>>();
        for (int i = 0; i < 2; i++) {
            ArrayList<BalanceList> tmp = new ArrayList<>();
            tmp.add(new BalanceList(this, 0.0005, i)); //默认创建一个万5利率的BalanceList
            balance.add(tmp);
        }
    }

    public String getAssetAttr() {
        return assetAttr;
    }

    public void setAssetAttr(String assetAttr) {
        this.assetAttr = assetAttr;
    }

    public String getProductAttr() {
        return productAttr;
    }

    public void setProductAttr(String productAttr) {
        this.productAttr = productAttr;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public int getWaive() {
        return waive;
    }

    public void setWaive(int waive) {
        this.waive = waive;
    }

    public List<List<BalanceList>> getBalance() {
        return balance;
    }

    public void setBalance(List<List<BalanceList>> balance) {
        this.balance = balance;
    }

    public boolean isFreeInt() {
        return freeInt;
    }

    public void setFreeInt(boolean freeInt) {
        this.freeInt = freeInt;
    }
}