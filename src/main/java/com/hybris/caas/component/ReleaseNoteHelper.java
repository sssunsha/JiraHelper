package com.hybris.caas.component;

import com.hybris.caas.model.ReleaseNote;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReleaseNoteHelper {

    public  void start(Map<String, ReleaseNote> map, String releaseReportFileLocation) {

        // generate the new release note map based on repository
        // here the map key is the repository
        Map<String, List<ReleaseNote>> releaseMap = new HashMap<>();
        map.forEach((key, value) -> {
            if (releaseMap.containsKey(value.repository)) {
                List<ReleaseNote> rnList = releaseMap.get(value.repository);
                rnList.add(value);
                releaseMap.put(value.repository, rnList);
            } else {
                List rns = new ArrayList();
                rns.add(value);
                releaseMap.put(value.repository, rns);
            }
        });

        // write the release note report to the file
        try {
            OutputStream f = new FileOutputStream(releaseReportFileLocation);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(f));
            releaseMap.forEach((key, value) -> {
                try {
                    Integer index = 1;
                    writer.write("# "+key);
                    writer.newLine();
                    value.forEach(v -> {
                        try {
                            writer.write(index.toString() + ". " + v.type + ": " + v.description);
                            writer.newLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    writer.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            writer.close();
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
