package io.github.dineshgpillai;

import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;
import org.fpml.legal.LegalDocument;
import org.fpml.legal.Party;

public class PartyExtractor extends ProcessFunction<LegalDocument, Party> {
    @Override
    public void processElement(LegalDocument legalDocument, Context context, Collector<Party> collector) throws Exception {

    }
}
