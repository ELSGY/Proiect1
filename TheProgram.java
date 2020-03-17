package loose.oose.fis.documents;

public abstract class Document {
    protected String[] continut;

    public Document(String[] continut) {
        this.continut = continut;
    }

    public abstract String[] analizeaza();

    @Override
    public String toString() {
        String res = "";
        for (String cuvant : continut) {
            res += cuvant + " ";
        }
        return res;
    }
}

package loose.oose.fis.documents;

import java.util.Arrays;

public class JSON extends Document {
    public JSON(String[] continut) {
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
        return "JSON " + super.toString();
    }
}

package loose.oose.fis.documents;

import java.util.Arrays;

public class XML extends Document {
    public XML(String[] continut) {
        super(continut);
    }

    @Override
    public String[] analizeaza() {
        String[] res = new String[continut.length];
        int      pos = 0;

        for (String cuvant : continut) {
            if (cuvant.charAt(0) != '<' || cuvant.charAt(cuvant.length() - 1) != '>') {
                res[pos] = cuvant;
                pos++;
            }
        }

        return Arrays.copyOf(res, pos);
    }

    @Override
    public String toString() {
        return "XML " + super.toString();
    }
}

package loose.oose.fis.processors;

import loose.oose.fis.documents.Document;

import java.util.ArrayList;

public interface Procesor {
    int proceseaza(ArrayList<Document> documente);
}

package loose.oose.fis.processors;

import loose.oose.fis.documents.Document;

import java.util.ArrayList;

public class ProcesorCautare implements Procesor {
    private String text;

    public ProcesorCautare(String text) {
        this.text = text;
    }

    @Override
    public int proceseaza(ArrayList<Document> documente) {
        int res = 0;

        for (Document document : documente) {
            String[] continut = document.analizeaza();
            for (String cuvant : continut) {
                if (cuvant.equals(text)) {
                    res++;
                }
            }
        }

        return res;
    }
}

package loose.oose.fis.processors;

import loose.oose.fis.documents.Document;

import java.util.ArrayList;

public class ProcesorCompus implements Procesor {
    private ArrayList<Procesor> procesoare = new ArrayList<>();

    public void adaugaProcesor(Procesor procesor) {
        procesoare.add(procesor);
    }

    @Override
    public int proceseaza(ArrayList<Document> documente) {
        int res = 0;

        for (Procesor procesor : procesoare) {
            res += procesor.proceseaza(documente);
        }

        return res;
    }
}

package loose.oose.fis;

import loose.oose.fis.documents.Document;
import loose.oose.fis.documents.JSON;
import loose.oose.fis.documents.XML;
import loose.oose.fis.processors.Procesor;
import loose.oose.fis.processors.ProcesorCautare;
import loose.oose.fis.processors.ProcesorCompus;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        String[] xmlList = new String[6];
        xmlList[0] = "<tag1>";
        xmlList[1] = "text1";
        xmlList[2] = "</tag1>";
        xmlList[3] = "<tag2>";
        xmlList[4] = "text2";
        xmlList[5] = "</tag2>";

        Document xml = new XML(xmlList);

        ArrayList<Document> documente = new ArrayList<>();
        documente.add(xml);

        Procesor c1 = new ProcesorCautare("text1");
        Procesor c2 = new ProcesorCautare("text3");
        Procesor c3 = new ProcesorCautare("text2");

        ProcesorCompus pc1 = new ProcesorCompus();
        pc1.adaugaProcesor(c1);
        pc1.adaugaProcesor(c2);

        ProcesorCompus pc2 = new ProcesorCompus();
        pc2.adaugaProcesor(pc1);
        pc2.adaugaProcesor(c3);

        System.out.println(pc2.proceseaza(documente));
    }
}
