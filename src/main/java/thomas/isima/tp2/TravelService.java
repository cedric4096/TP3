package thomas.isima.tp2;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Range;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Currency;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 Les trajets de moins de 10 Km sont remboursés à hauteur de 1.50€ du Km
 Les trajets de 10 Km à 39 Km sont remboursés à hauteur de 0.40€ du Km
 Les trajets de 40 Km à 60 Km sont remboursés à hauteur de 0.55€ du Km
 Par tranche de 20Km au dela de 60Km 6.81€
 */
@Service
@Slf4j
public class TravelService {

    public static Logger log = LoggerFactory.getLogger("TravelService");

    private static final float MAX_RANGE = 60f;
    private static final float BATCH_SIZE = 20f;
    private static final float BATCH_COST = 6.81f;
    //
    private static Map<Range<Float>,Float> data = new LinkedHashMap<>();

    private static DecimalFormat df = new DecimalFormat();

    public TravelService() {
        data.put(Range.between(0f, 10f), 1.5f);
        data.put(Range.between(10f, 40f), 0.40f);
        data.put(Range.between(40f, 60f), 0.55f);
        df.setCurrency(Currency.getInstance("EUR"));
        df.setMaximumFractionDigits(2);
    }


    Float feeCalculator(float dist){
        if(dist<=0.0f){
            log.error("Le kilomètrage {} n'est pas valide ",dist);
            return null;
        }
        Float fee= inRange(dist);
        fee+= extraRange(dist);
        Float formatFee =onlyTwoDigit(fee);
        log.info("Pour les {} Km parcourus, les frais de route sont de {}",dist,formatFee);
        return formatFee;


    }

    Float inRange(Float dist){
        Float fee = 0f;
        for(Map.Entry<Range<Float>, Float> e : data.entrySet()){
            Range<Float> r = e.getKey();
            int compare = r.elementCompareTo(dist);
            Float rmin = r.getMinimum();
            switch (compare) {
                case -1 :
                    return fee;
                case 0 :
                    fee+= (dist - rmin)*data.get(r);
                    log.trace("Il y a {} Km dans la tranche {} soit {}",dist - rmin,r,(dist - rmin)*data.get(r));
                    break;
                case 1 :
                    fee+=(r.getMaximum()-rmin)*data.get(r);
                    log.trace("Il y a {} Km dans la tranche {} soit {}",r.getMaximum()-rmin,r,(r.getMaximum()-rmin)*data.get(r));
                    break;
                default:
                    //can't be reach
                    break;
            }
        }
        return fee;
    }

    Float extraRange(Float dist){
        Double tranches =  Math.floor(Math.max(dist- MAX_RANGE,0) / BATCH_SIZE);
        Float extra = (float) (tranches* BATCH_COST);
        log.debug("Pour {} Km il y a {} tranche{} de {}Km supplémentaires",dist,Math.round(tranches),((Math.round(tranches)>1)?"s":""),BATCH_SIZE);
        return extra;
    }

    /**
     * Retourne un montant arrondit avec maximum 2 digit
     * @param fee le montant à arrondir
     * @return un montant arrondit avec maximum 2 digit
     */
    Float onlyTwoDigit(Float fee){
        return Float.parseFloat(df.format(fee).replace(",","."));
    }
}

