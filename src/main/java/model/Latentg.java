package model;

import model.role.person.Role;

public class Latentg extends Cog{
	public Latentg(FaseBoard sb, Role latent) {
		super(sb,latent);
		
	}
	
	void countSizes() {
		size = this.playerList.size();
		trueSize = sb.getCogList().stream()
				.map(a -> a.size()-a.hasWws)
				.reduce(sr.getVillsList().size(),(a,b) -> a-b);

		hasWws = size - trueSize;
	}

	void updateTruePer() {
		if (!isFull) {
			return;
			//あとで実装
			//truePer=100%?
		}

		float truePer = trueSize / size;
		float onePer = sr.getPlayerList().stream()
				.filter(a -> sb.getCogMap().get(a.getRole()).getIsFull()

						.map(a -> a.getTrueSize())
				.reduce(sr.getVillsList().size(),(a,b) -> a-b);
		
		
		for (Player player : playerList) {
			player.setVillsPer((float) Math.ceil(truePer * 10000) / 10000);
			player.setWwsPer((float) Math.ceil((1 - truePer) * 10000) / 10000);
			player.getTruePerMap().put(this.role, truePer);
			player.getTruePerMap().replaceAll((key, value) -> key.equals(this.role) ? value : 0);
		}
	}
}
