package wargame.config;

import java.io.Serializable;

public class SavedConfig implements Serializable {
	private static final long serialVersionUID = -6544400139883295851L;
	private IConfig config;

	public SavedConfig() {}

	public boolean isActive() {
		return config != null;
	}

	public void setConfig(IConfig config) {
		this.config = config;
	}

	public IConfig getConfig() {
		return config;
	}
}
