package io.github.dineshgpillai;

import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;
import org.fpml.legal.LegalDocument;
import org.fpml.legal.Party;

import java.util.List;

public class PartyExtractor extends ProcessFunction<List<Party>, Party> {
    @Override
    public void processElement(List<Party> legalDocument, Context context, Collector<Party> collector) throws Exception {


    }
}
