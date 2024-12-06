package model;

import java.util.List;

import model.role.person.Role;

public class Latentg extends Cog {
	public Latentg(FaseBoard sb, Role latent, List<Player> playerList) {
		super(sb, latent, playerList);
	}

	@Override
	void countSizes() {
		size = this.playerList.size();
		trueSize = sb.getCogList().stream()
				.map(a -> a.size() - a.hasWws)
				.reduce(sr.getVillsList().size(), (a, b) -> a - b);

		if (size == 0)
			return;

		truePer = (float) trueSize / size;
		hasWws = size - trueSize;
		System.out
				.println("latentg,truePer:" + truePer + ",trueSize:" + trueSize + ",size:" + size + ",hasWws" + hasWws);
	}

	@Override
	void updateTruePer() {
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
