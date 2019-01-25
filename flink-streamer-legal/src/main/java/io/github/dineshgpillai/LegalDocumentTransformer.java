package io.github.dineshgpillai;

import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;
import org.fpml.legal.LegalDocument;
import org.fpml.legal.Party;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.List;

public class LegalDocumentTransformer extends ProcessFunction<String, LegalDocument> {

    final OutputTag<List<Party>> outputTag = new OutputTag<List<Party>>("party-stream") {
    };

    @Override
    public void processElement(String s, ProcessFunction<String, LegalDocument>.Context ctx, Collector<LegalDocument> collector) throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(org.fpml.legal.LegalDocument.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        JAXBElement<LegalDocument> obj= (JAXBElement) jaxbUnmarshaller.unmarshal(new File(s));

        LegalDocument ld= obj.getValue();
        collector.collect(ld);
        ctx.output(outputTag, ld.getParty());

    }
}
