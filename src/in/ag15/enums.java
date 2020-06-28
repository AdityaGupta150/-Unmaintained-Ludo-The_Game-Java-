package in.ag15;

public class enums{
	enum Colour{
		LAAL,
		HARA,
		PEELA,
		NEELA
	}

	enum Player{
		Player1,
		Player2,
		Player3,
		Player4
	}

	enum Direction{
		NORTH,
		EAST,
		SOUTH,
		WEST
	}

	static public class ProfitType{
		static final int NORMAL_MOVE = 1;
		static final int REACH_HOME = 2;
		static final int REACH_STOP = 3;
		static final int ATTACK = 4;
		static final int UNLOCK = 5;
		static final int CROSSES_ENEMY = -3;
	}

	enum BoxType{
		UNUSABLE,
		LOCK,
		NORMAL,
		STOP,
		HOME_PATH,
		HOME_END,
		Inner_TURN,
		Outer_TURN
	}

	enum RobotKind{
		randomRobo,
		thinkerRobo
	}
}