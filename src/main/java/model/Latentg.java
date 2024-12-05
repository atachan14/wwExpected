package model;

import model.role.person.Role;

public class Latentg extends Cog {
	public Latentg(FaseBoard sb, Role latent) {
		super(sb, latent);

	}

	void countSizes() {
		size = this.playerList.size();
		trueSize = sb.getCogList().stream()
				.map(a -> a.size() - a.hasWws)
				.reduce(sr.getVillsList().size(), (a, b) -> a - b);

		truePer = trueSize / size;
		hasWws = size - trueSize;
	}

	void updateVillsPer() {
		if (!isFull) {
			return;
			//あとで実装
			//truePer=100%?
		}

		
		
		for (Player player : playerList) {
			player.setVillsPer((float) Math.ceil(truePer * 10000) / 10000);
			player.setWwsPer((float) Math.ceil((1 - truePer) * 10000) / 10000);
		}
	}
}
