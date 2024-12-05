package model;

import java.util.stream.Collectors;

import model.role.person.Role;

public class Latentg extends Cog {
	public Latentg(FaseBoard sb, Role latent) {
		super(sb, latent);
		this.sb = sb;
		this.role = latent;
	}

	void countSizes() {
		size = this.playerList.size();
		trueSize = sb.getCogList().stream()
				.map(a -> a.size() - a.hasWws)
				.reduce(sr.getVillsList().size(), (a, b) -> a - b);
		
		if (size == 0)
			return;

		truePer = trueSize / size;
		hasWws = size - trueSize;
	}
	void criatePlayerList() {
		playerList = sb.getPlayerList().stream()
				.filter(a -> a.getCo() == role)
				.collect(Collectors.toList());
	}
	
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
