package com.tracfone.testcaseapp.Entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ENV_DETAILS database table.
 * 
 */
@Embeddable
public class EnvDetailPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String envname;

	@Column(name="\"DOMAIN\"")
	private long domain;

	public EnvDetailPK() {
	}
	public String getEnvname() {
		return this.envname;
	}
	public void setEnvname(String envname) {
		this.envname = envname;
	}
	public long getDomain() {
		return this.domain;
	}
	public void setDomain(long domain) {
		this.domain = domain;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EnvDetailPK)) {
			return false;
		}
		EnvDetailPK castOther = (EnvDetailPK)other;
		return 
			this.envname.equals(castOther.envname)
			&& (this.domain == castOther.domain);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.envname.hashCode();
		hash = hash * prime + ((int) (this.domain ^ (this.domain >>> 32)));
		
		return hash;
	}
}