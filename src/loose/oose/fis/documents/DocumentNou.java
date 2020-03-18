package loose.oose.fis.documents;

import java.util.Arrays;

public class DocumentNou extends Document {

    public DocumentNou(String[] continut) {
        super(continut);
    }

    @Override
    public String[] analizeaza() {
        String[] res = new String[continut.length];
        int      pos = 0;

        for (String cuvant : continut) {
            if (!cuvant.contains(":")) {
                res[pos] = cuvant;
                pos++;
            }
        }

        return Arrays.copyOf(res, pos);
    }

    @Override
    public String toString() {
        return "DocumentNou " + super.toString();
    }
}
