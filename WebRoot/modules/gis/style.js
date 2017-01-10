/**
 * Created by swl on 2016/12/23.
 */
var carStyle = new ol.style.Style({
    /*  image: new ol.style.Icon(/!** @type {olx.style.IconOptions} *!/ ({
     src:path+'/common/images/gis/delete.png',
     anchor: [0.5, 0.8],
     })),*/
    image: new ol.style.Circle({
        radius: 7,
        snapToPixel: false,
        fill: new ol.style.Fill({color: 'black'}),
        stroke: new ol.style.Stroke({
            color: 'white', width: 2
        })
    })
});
var startStyle = new ol.style.Style({
    image: new ol.style.Icon(/** @type {olx.style.IconOptions} */ ({
        anchor: [0.5, 36],
        // offset:[0,15],
        opacity: 0.8,
        src: path+'/common/images/gis/icon_st.png',
        anchorYUnits: 'pixels',
        /*anchorXUnits: 'fraction',
         anchorYUnits: 'pixels',
         opacity: 0.75,*/

    }))
});
var endStyle = new ol.style.Style({
    image: new ol.style.Icon(/** @type {olx.style.IconOptions} */ ({
        anchor: [0.5, 36],
        // offset:[0,15],
        opacity: 0.8,
        src: path+'/common/images/gis/icon_en.png',
        anchorYUnits: 'pixels',
    }))
});

var crossStyle= new ol.style.Style({
    fill: new ol.style.Fill({
        color: '#F2CC79'
    }),
    stroke: new ol.style.Stroke({
        color: '#F2CC79',
        width: 4
    })
});