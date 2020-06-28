package in.ag15;

/*Checked exceptions are exceptions that need to be treated explicitly*/

public class CustomExceptions {
    static public class OutOfBound extends Exception{
        private static final long serialVersionUID = 1L;

        public OutOfBound(String prob) {
            super(prob);
        }
    }
    static public class GotiNotAvailable extends Exception{
        private static final long serialVersionUID = 1L;

        public GotiNotAvailable(String prob) {
            super(prob);
        }
    }
    static public class endApplication extends Exception{
        private static final long serialVersionUID = 1L;

        public endApplication(String prob) {
            super(prob);
        }
    }
}