-- create tables
                CREATE CACHED TABLE dataset
                        (
                        id BIGINT PRIMARY KEY,
                        dropsize DOUBLE,
                        distance INTEGER,
                        drop_falling_speed INTEGER,
                        human_speed INTEGER,
                        human_height INTEGER,
                        human_width INTEGER,
                        human_depth INTEGER,
                        space_width INTEGER,
                        space_height INTEGER,
                        space_lenght INTEGER,
                        rain_intensyty DOUBLE,
                        probability_drop_in_cell DOUBLE,
                        drops_in_layer DOUBLE
                        );

                CREATE CACHED TABLE results
                        (
                        id BIGINT IDENTITY,
                        iteration BIGINT,
                        dataset_id BIGINT,
                        cur_distance INTEGER,
                        top_drops BIGINT,
                        front_drops BIGINT,
                        FOREIGN KEY(dataset_id) REFERENCES dataset(id)
                        );

                        /*
-- Показатели:
-- забег
-- - объем полученной воды;
-- - количество капель всего;
-- - количество (объем) капель сверху всего;
-- - количество (объем) капель спереди всего;
-- - средний прирост за один тик;
-- - средний прирост за один тик (не включая фронтальные);
-- - средний приросn за одно горизонтальное смещение (всего / только горизонтальные/ только фронтальные)
-- сет
-- - разница полученного объема на разных скоростях.
-- - другие разницы

 */

                 CREATE CACHED TABLE total
                        (
                        id BIGINT IDENTITY,
                        dataset_id BIGINT,

                        drop_volume DOUBLE,
                        total_volume DOUBLE,

                        total_drops BIGINT,
                        total_top BIGINT,
                        total_front BIGINT,

                        delta_per_tic_total DOUBLE,
                        delta_per_tic_top DOUBLE,
                        delta_per_step DOUBLE,
                        total_tics INT,

                        delta_per_step_top DOUBLE,
                        delta_per_step_front DOUBLE,


                        FOREIGN KEY(dataset_id) REFERENCES dataset(id)
                        );