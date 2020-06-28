package in.ag15;

import in.ag15.enums.Colour;
import in.ag15.enums.Direction;
import javafx.util.Pair;

import java.awt.Point;
import java.lang.reflect.Array;
import java.util.*;

public class Ludo_Coords {

	static final Map<Point, Direction> OuterCorners;
	static final Map<Point, Direction> InnerTurns;
	static final Map<Colour, Pair<Point, Direction>> HomeTurns;

	static final Map<Colour, Pair<Point, Direction>> StartPos;

	static final ArrayList<Point> Stops;

	static Direction turnAtCorner(final Point currCoords, final Map<Point, Direction> cornersVec){
		return cornersVec.getOrDefault(currCoords, null);
	}
	
	static{
		
		OuterCorners = Map.of(
			new Point(0,6), Direction.EAST,
			new Point(0,8), Direction.SOUTH,
			new Point(6,0), Direction.EAST,
			new Point(6,14), Direction.SOUTH,
			new Point(8,0), Direction.NORTH,
			new Point(8,14), Direction.WEST,
			new Point(14,6), Direction.NORTH,
			new Point(14,8), Direction.WEST
		);

		InnerTurns = Map.of(
			new Point(9,6), Direction.WEST,
			new Point(6,5), Direction.NORTH,
			new Point(8,9), Direction.SOUTH,
			new Point(5,8), Direction.EAST
		);

		StartPos = Map.of(
			Colour.LAAL, new Pair<>( new Point(13,6), Direction.NORTH ),
			Colour.HARA, new Pair<>(new Point(6,1), Direction.EAST),
			Colour.PEELA, new Pair<>(new Point(1,8), Direction.SOUTH),
			Colour.NEELA, new Pair<>(new Point(8,13), Direction.WEST)	
		);

		Stops = new ArrayList<Point>(){
			{
				add(new Point(2,6));
				add(new Point(6,12));
				add(new Point(12,8));
				add(new Point(8,2));
			}
		};
		for (Colour c : StartPos.keySet()) {	//Copying start_coords to stops, since their positions are stops too
			Stops.add( StartPos.get(c).getKey() );
		}

		HomeTurns = Map.of(
			Colour.LAAL, new Pair<>( new Point(14,7), Direction.NORTH),
			Colour.HARA, new Pair<>( new Point(7,0), Direction.EAST),
			Colour.PEELA, new Pair<>( new Point(0,7), Direction.SOUTH ),
			Colour.NEELA, new Pair<>( new Point(7,14), Direction.WEST )			
		);
	}

}