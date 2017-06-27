package rules.drools;

/**
 * Created by mwjsolar on 16/11/29.
 */
public class DroolsObject {
        private String name;

        private String result;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "DroolsObject{" +
                "name='" + name + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
