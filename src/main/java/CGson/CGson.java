package CGson;

import Model.Trendyol.TrendyolModel;
import Writer.WriteFile;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONObject;

import java.lang.reflect.Type;

public class CGson {
    public TrendyolModel convertTrendyolModel(JSONObject jsonObject){
        Gson gson = new Gson();
        Type type = new TypeToken<TrendyolModel>(){}.getType();
        TrendyolModel trendyolModel = gson.fromJson(String.valueOf(jsonObject), type);
        return trendyolModel;
    }

}
