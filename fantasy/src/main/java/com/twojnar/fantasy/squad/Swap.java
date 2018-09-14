package com.twojnar.fantasy.squad;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.twojnar.fantasy.common.View;
import com.twojnar.fantasy.player.Player;
import com.twojnar.fantasy.player.PlayerProfile;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonView(View.PublicGeneral.class)
public class Swap {
		
		private PlayerProfile out;
		private PlayerProfile in;
		private long benefit;
		
		public Swap(PlayerProfile out, PlayerProfile in, long benefit) {
			this.out = out;
			this.in = in;
			this.benefit = benefit;
		}
		
		public PlayerProfile getOut() {
			return out;
		}

		public void setOut(PlayerProfile out) {
			this.out = out;
		}

		public PlayerProfile getIn() {
			return in;
		}

		public void setIn(PlayerProfile in) {
			this.in = in;
		}

		public long getBenefit() {
			return benefit;
		}

		public void setBenefit(long benefit) {
			this.benefit = benefit;
		}
		
		
	}