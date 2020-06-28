package in.ag15;

import java.util.HashMap;
import java.util.Map;

import in.ag15.enums.Colour;

public class keywords {

    static final String robo = "ROBOT";
    static final String thinker = "JAI HIND";   //Maine banaya to mai jo naam rakhu, meri marzi, Kamse kam kisi samajhdar robot ke saath khelna hai, then you will have to type this WARCRY!!
                                                    //But, if you are seeing this, please do tell, if you are having problems, creating 'your own' editted version :D

    static final Map<Colour, Character> colourCodes = Map.of(
        Colour.LAAL, 'R',
        Colour.HARA, 'G',
        Colour.PEELA, 'Y',
        Colour.NEELA, 'B'
    );
}