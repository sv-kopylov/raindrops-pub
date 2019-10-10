truncate table results;
truncate table dataset;

select dropsize, drop_falling_speed, rain_intensity, drops_in_layer from dataset;
select * from dataset;
select count(*) from results;

select iteration, cur_distance, top_drops, front_drops from results
where dataset_id = 1570203375075
order by iteration;

select iteration, cur_distance, top_drops, front_drops from results
where dataset_id = 1570203375076
order by iteration;

select count(*) from results
where dataset_id = 1569865079300;