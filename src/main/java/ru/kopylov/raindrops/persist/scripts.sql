--truncate table results;
--truncate table total;
--truncate table dataset;

--drop table total;


select * from total;

select * from dataset;
select * from results;

select dropsize, drop_falling_speed, rain_intensity, drops_in_layer from dataset;

select count(*) from results;

select iteration, cur_distance, top_drops, front_drops from results
where dataset_id = 1570726691026
order by iteration;

select iteration, cur_distance, top_drops, front_drops from results
where dataset_id = 1570203375076
order by iteration;

select count(*) from results
where dataset_id = 1569865079300;

-- количество капель полученных в забеге
select MAX(top_drops) top, MAX(front_drops) front, (MAX(top_drops) + MAX(front_drops)) hole from results
where dataset_id = 1570726691071;

select count(id) from results where dataset_id=1570726691071;
select * from results where dataset_id=1570726691071;

select MAX(top_drops) top from results where dataset_id = 1570726691071;


select count(distinct ) from results where dataset_id=1570726691071;

select
t.dataset_id,
rain_intensyty*3600 intensity,
dropsize,
(human_speed*3600)/100000 human_speed,
total_volume*1000 total_volume,
total_drops,
total_top,
total_front,
delta_per_tic_total,
delta_per_tic_top,
delta_per_step,
delta_per_step_top,
delta_per_step_front,
drop_falling_speed,
total_tics,
drop_volume,
drops_in_layer
from total t, dataset d
where t.dataset_id = d.id
order by
rain_intensyty,
dropsize,
human_speed

-- TODO исправить интенсити (ошибка в слове)
-- интенсивность - размер капли - скорость
select
dataset_id,
rain_intensyty*3600 intensity,
dropsize,
(human_speed*3600)/100000 human_speed,
total_volume*1000 total_volume
from total t, dataset d
where t.dataset_id = d.id
order by
rain_intensyty,
dropsize,
human_speed
