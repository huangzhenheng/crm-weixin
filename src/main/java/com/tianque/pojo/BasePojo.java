package com.tianque.pojo;

import java.io.Serializable;

public class BasePojo implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		if (id != null) {
			final int prime = 31;
			int result = 1;
			result = prime * (prime * result + getClass().hashCode())
					+ id.hashCode();
			return result;
		}
		return super.hashCode();
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof BasePojo)) {
			return false;
		}

		if ((getClass().isAssignableFrom(obj.getClass()))
				|| (obj.getClass().isAssignableFrom(getClass()))) {

		} else {
			return false;
		}

		BasePojo other = (BasePojo) obj;
		if (other.getId() == null || getId() == null) {
			return false;
		} else {
			if (other.getId().equals(getId())) {
				return true;
			} else {
				return false;
			}
		}
	}

}
