package lesson10.prob2;

import java.util.Objects;

public class Account {
	private int acctId;
	private double balance;
	
	public Account(int id, double startBalance) {
		if(startBalance <= 0) throw new IllegalArgumentException("Start balance must be > 0!");
		acctId = id;
		balance = startBalance;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Account account = (Account) obj;
		return acctId == account.acctId && Double.compare(account.balance, balance) == 0;
	}

	@Override
	public int hashCode() {
		return Objects.hash(acctId, balance);
	}

	public int getAcctId() {
		return acctId;
	}
	public double getBalance() {
		return balance;
	}
	 
}
