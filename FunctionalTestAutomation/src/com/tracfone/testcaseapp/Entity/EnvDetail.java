package com.tracfone.testcaseapp.Entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ENV_DETAILS database table.
 * 
 */
@Entity
@Table(name="ENV_DETAILS")
@NamedQuery(name="EnvDetail.findAll", query="SELECT e FROM EnvDetail e")
public class EnvDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EnvDetailPK id;

	private String envcontext;

	public EnvDetail() {
	}

	public EnvDetailPK getId() {
		return this.id;
	}

	public void setId(EnvDetailPK id) {
		this.id = id;
	}

	public String getEnvcontext() {
		return this.envcontext;
	}

	public void setEnvcontext(String envcontext) {
		this.envcontext = envcontext;
	}

}