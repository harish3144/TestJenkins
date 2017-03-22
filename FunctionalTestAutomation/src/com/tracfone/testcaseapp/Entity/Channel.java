package com.tracfone.testcaseapp.Entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the CHANNEL database table.
 * 
 */
@Entity
@NamedQuery(name="Channel.findAll", query="SELECT c FROM Channel c")
public class Channel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CHANNEL_CHANNELID_GENERATOR", sequenceName="CHANNEL_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CHANNEL_CHANNELID_GENERATOR")
	private long channelid;

	private String channelname;

	public Channel() {
	}

	public long getChannelid() {
		return this.channelid;
	}

	public void setChannelid(long channelid) {
		this.channelid = channelid;
	}

	public String getChannelname() {
		return this.channelname;
	}

	public void setChannelname(String channelname) {
		this.channelname = channelname;
	}

}